package com.project.najdiprevoz.services

import com.project.najdiprevoz.domain.Ride
import com.project.najdiprevoz.enums.NotificationType
import com.project.najdiprevoz.enums.RideRequestStatus
import com.project.najdiprevoz.enums.RideStatus
import com.project.najdiprevoz.exceptions.RideNotFoundException
import com.project.najdiprevoz.repositories.*
import com.project.najdiprevoz.web.request.FilterTripRequest
import com.project.najdiprevoz.web.request.create.CreateTripRequest
import com.project.najdiprevoz.web.request.edit.EditTripRequest
import com.project.najdiprevoz.web.response.PastTripResponse
import com.project.najdiprevoz.web.response.TripDetailsResponse
import com.project.najdiprevoz.web.response.TripResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.transaction.Transactional

//TODO: AVOID USING THIS MUCH SERVICES

@Service
class TripService(private val repository: RideRepository,
                  private val userService: UserService,
                  private val cityService: CityService,
                  private val notificationService: NotificationService) {

    val logger: Logger = LoggerFactory.getLogger(TripService::class.java)

    fun findAllActiveRides(): List<TripResponse> =
            repository.findAllByStatus(RideStatus.ACTIVE).map { it.mapToTripResponse() }

    fun findAllActiveTripsForToday(): List<TripResponse> =
            repository.findAll(evaluateSpecification(listOf("departureTime"), ZonedDateTime.now(), ::laterThanTime))
                    .map { it.mapToTripResponse() }

    fun createNewTrip(createTripRequest: CreateTripRequest, username: String) {
        logger.info("[RideService - ADD RIDE] Creating new ride!")
        repository.save(createRideObject(createTripRequest = createTripRequest, username = username))
    }

    fun getPastPublishedTripsByUser(userId: Long) =
            repository.findAllByDriverIdAndStatus(driverId = userId, status = RideStatus.FINISHED)
                    .map { it.mapToTripResponse() }

    fun getMyPastTrips(username: String): List<PastTripResponse> {
        return repository.findMyPastTrips(username).map { mapToPastTripResponse(it, username) }
    }

    @Modifying
    @Transactional
    fun cancelTrip(rideId: Long) {
        val ride = findById(rideId)
        ride.status = RideStatus.CANCELLED
        ride.rideRequests = ride.rideRequests.map { it.copy(status = RideRequestStatus.RIDE_CANCELLED) }
        ride.rideRequests.forEach { notificationService.pushRequestStatusChangeNotification(it, NotificationType.RIDE_CANCELLED) }
        repository.save(ride)
        logger.info("[RideService - CANCEL RIDE] Ride with id $rideId successfully cancelled!")
    }

    fun findById(id: Long): Ride =
            repository.findById(id).orElseThrow { RideNotFoundException("Ride with id $id was not found") }

    fun getAllTripsForUser(userId: Long) =
            repository.findAllByDriverId(driverId = userId).map { it.mapToTripResponse() }


    fun findRidesByFromLocationAndDestination(from: String, to: String): List<TripResponse> =
            repository.findAllByFromLocationNameAndDestinationName(from, to)
                    .filter { it.status == RideStatus.ACTIVE && it.getAvailableSeats() > 0 }
                    .map { it.mapToTripResponse() }

    @Modifying
    @Transactional
    fun editTrip(rideId: Long, editTripRequest: EditTripRequest): TripResponse = with(editTripRequest) {
        logger.info("[RideService - Edit Ride] Editing ride with ID:[$rideId]..")
        repository.save(findById(rideId).copy(
                fromLocation = cityService.findByName(fromLocation),
                departureTime = departureTime,
                destination = cityService.findByName(toLocation),
                additionalDescription = description,
                pricePerHead = pricePerHead)).mapToTripResponse()
    }

    fun checkForFinishedTripsCronJob() {
        logger.info("[CRONJOB] Checking for finished rides..")
        logger.info("[CRONJOB] Updated [" + repository.updateRidesCron(ZonedDateTime.now()) + "] rides.")
    }

    fun findAllFiltered(req: FilterTripRequest): List<TripResponse> = with(req) {
        val specification = if (departureDate != null)
            createRideSpecification(fromAddress = fromLocation, toAddress = toLocation,
                    departure = ZonedDateTime.of(
                            LocalDate.ofInstant(departureDate.toInstant(),
                                    ZoneId.systemDefault()), LocalTime.MIN,
                            ZoneId.systemDefault())) //TODO: refactor this

        else createRideSpecification(fromAddress = fromLocation, toAddress = toLocation,
                departure = ZonedDateTime.of(LocalDate.now(), LocalTime.MIN,
                        ZoneId.systemDefault()))

        if (requestedSeats != null) {
            return repository.findAll(specification)
                    .filter { it.getAvailableSeats() >= requestedSeats }
                    .map { it.mapToTripResponse() }
        }
        return repository.findAll(specification).map { it.mapToTripResponse() }
    }

//    private fun convertToTripResponse(ride: Ride): TripResponse = ride.mapToTripResponse()

    private fun createRideSpecification(fromAddress: Long, toAddress: Long, departure: ZonedDateTime) =
            listOfNotNull(
                    evaluateSpecification(listOf("fromLocation", "id"), fromAddress.toString(), ::likeSpecification),
                    evaluateSpecification(listOf("destination", "id"), toAddress.toString(), ::likeSpecification),
                    evaluateSpecification(listOf("departureTime"), departure, ::laterThanTime),
                    evaluateSpecification(listOf("status"), RideStatus.ACTIVE, ::tripStatusEqualsSpecification)
            ).fold(whereTrue()) { first, second ->
                Specification.where(first).and(second)
            }

    private fun createRideObject(createTripRequest: CreateTripRequest, username: String) = with(createTripRequest) {
        Ride(
                createdOn = ZonedDateTime.now(),
                fromLocation = cityService.findById(fromLocation),
                destination = cityService.findById(destination),
                departureTime = departureTime,
                totalSeatsOffered = totalSeats,
                driver = userService.findUserByUsername(username),
                pricePerHead = pricePerHead,
                additionalDescription = additionalDescription,
                rideRequests = listOf(),
                status = RideStatus.ACTIVE,
                isSmokingAllowed = smokingAllowed,
                isPetAllowed = petAllowed,
                hasAirCondition = hasAirCondition,
                maxTwoBackSeat = maxTwoBackseat)
    }

    private inline fun <reified T> evaluateSpecification(value: T?, fn: (T) -> Specification<Ride>) = value?.let(fn)
    private inline fun <reified T> evaluateSpecification(properties: List<String>, value: T?,
                                                         fn: (List<String>, T) -> Specification<Ride>) = value?.let {
        fn(properties, value)
    }

    fun getTripAdditionalInfo(tripId: Long): TripDetailsResponse {
        return mapToTripDetailsResponse(findById(tripId))
    }

    private fun mapToTripDetailsResponse(trip: Ride): TripDetailsResponse = with(trip) {
        return TripDetailsResponse(isPetAllowed = isPetAllowed,
                isSmokingAllowed = isSmokingAllowed,
                hasAirCondition = hasAirCondition,
                additionalDescription = trip.additionalDescription)
    }

    private fun mapToPastTripResponse(ride: Ride, username: String) = with(ride) {
        PastTripResponse(
                tripId = id,
                from = fromLocation.name,
                to = destination.name,
                pricePerHead = pricePerHead,
                driver = driver.mapToUserShortResponse(),
                canSubmitRating = canSubmitRating(ride, username)

        )
    }

    private fun canSubmitRating(ride: Ride, username: String): Boolean {
        return repository.canSubmitRating(username, ride).isEmpty()
    }

//    @PostConstruct
//    fun editRideTest() {
//        val t = EditTripRequest(fromLocation = "Valandovo", toLocation = "Kumanovo",
//                pricePerHead = 12500, departureTime = ZonedDateTime.now(), description = "ahaaa")
//        this.editRide(1, t)
//        val p = findById(1)
//        logger.warn("P$p")
//    }

//    @PostConstruct
//    fun cancelRideTest() {
//        val t = cancelTrip(1L)
//    }
}

