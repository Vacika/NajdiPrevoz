package com.project.najdiprevoz.services

import com.project.najdiprevoz.domain.Ride
import com.project.najdiprevoz.domain.RideRequest
import com.project.najdiprevoz.enums.NotificationType
import com.project.najdiprevoz.enums.RequestStatus
import com.project.najdiprevoz.enums.RideStatus
import com.project.najdiprevoz.exceptions.RideNotFoundException
import com.project.najdiprevoz.repositories.*
import com.project.najdiprevoz.web.request.FilterTripRequest
import com.project.najdiprevoz.web.request.create.CreateTripRequest
import com.project.najdiprevoz.web.request.edit.EditTripRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import javax.transaction.Transactional

@Service
class TripService(private val repository: RideRepository,
                  private val userService: UserService,
                  private val cityService: CityService,
                  private val notificationService: RideNotificationService) {

    val logger: Logger = LoggerFactory.getLogger(TripService::class.java)

    fun findAllActiveRides(): List<Ride> =
            repository.findAllByStatus(RideStatus.ACTIVE)

    fun findAllActiveTripsWithAvailableSeats() =
            findAllActiveRides().filter { it.getAvailableSeats() > 0 }

    fun createNewRide(createTripRequest: CreateTripRequest): Ride =
            repository.save(createRideObject(createTripRequest = createTripRequest))

    fun getPastRidesForUser(userId: Long) =
            repository.findAllByDriverIdAndStatus(driverId = userId, status = RideStatus.FINISHED)

    @Modifying
    @Transactional
    fun deleteRide(rideId: Long) {
        var ride = findById(rideId)
        ride.status = RideStatus.CANCELLED
        ride.rideRequests = ride.rideRequests.map { it.copy(status = RequestStatus.RIDE_CANCELLED) }
        repository.save(ride)
        logger.info("[RideService - DELETE RIDE] Ride with id $rideId successfully deleted!")
    }

    fun findById(id: Long): Ride =
            repository.findById(id).orElseThrow { RideNotFoundException("Ride with id $id was not found") }

    fun findAllRidesForUser(userId: Long) =
            repository.findAllByDriverId(driverId = userId)

    private fun createRideObject(createTripRequest: CreateTripRequest) = with(createTripRequest) {
        Ride(
                createdOn = ZonedDateTime.now(),
                fromLocation = cityService.findByName(fromLocation),
                destination = cityService.findByName(destination),
                departureTime = departureTime,
                totalSeatsOffered = totalSeats,
                driver = userService.findUserById(driverId),
                pricePerHead = pricePerHead,
                additionalDescription = additionalDescription,
                rideRequests = listOf<RideRequest>(),
                status = RideStatus.ACTIVE
        )
    }

    fun findFromToRides(from: String, to: String): List<Ride> =
            repository.findAllByFromLocationNameAndDestinationName(from, to)
                    .filter { it.status == RideStatus.ACTIVE && it.getAvailableSeats() > 0 }

    fun editRide(rideId: Long, editTripRequest: EditTripRequest): Ride = with(editTripRequest) {
        repository.save(findById(rideId).copy(fromLocation = cityService.findByName(fromLocation),
                departureTime = departureTime,
                destination = cityService.findByName(toLocation),
                additionalDescription = description,
                pricePerHead = pricePerHead))
    }

    fun checkForFinishedRidesTask() {
        logger.info("[CRONJOB] Checking for finished rides..")
        logger.info("[CRONJOB] Updated [" + repository.updateRidesCron(ZonedDateTime.now()) + "] rides.")
    }

    private fun pushNotification(req: RideRequest, type: NotificationType) {
        notificationService.pushRequestStatusChangeNotification(req, type)
    }

    fun findAllFiltered(req: FilterTripRequest): List<Ride> = with(req) {
        val specification = createRideSpecification(fromAddress = fromAddress, toAddress = toAddress, departure = departure)
        if (requestedSeats != null) {
            return repository.findAll(specification).filter { it.getAvailableSeats() >= requestedSeats }
        }
        return repository.findAll(specification)
    }

    private fun createRideSpecification(fromAddress: String?, toAddress: String?, departure: ZonedDateTime?) =
            listOfNotNull(
                    evaluateSpecification(listOf("fromLocation", "name"), fromAddress, ::likeSpecification),
                    evaluateSpecification(listOf("destination", "name"), toAddress, ::likeSpecification),
                    evaluateSpecification(listOf("departureTime"), departure, ::laterThanTime),
                    evaluateSpecification(listOf("status"), RideStatus.ACTIVE, ::tripStatusEqualsSpecification)
            ).fold(whereTrue()) { first, second ->
                Specification.where(first).and(second)
            }

    private inline fun <reified T> evaluateSpecification(value: T?, fn: (T) -> Specification<Ride>) = value?.let(fn)
    private inline fun <reified T> evaluateSpecification(properties: List<String>, value: T?, fn: (List<String>, T) -> Specification<Ride>) = value?.let { fn(properties, value) }


    //    @PostConstruct
    fun editRideTest() {
        val t = EditTripRequest(fromLocation = "Valandovo", toLocation = "Kumanovo",
                pricePerHead = 12500, departureTime = ZonedDateTime.now(), description = "ahaaa")
        this.editRide(1, t)
        val p = findById(1)
        logger.warn("P$p")
    }

    //    @PostConstruct
    fun deleteRideTest() {
        val t = deleteRide(1L)
    }
//    fun getAvailableSeatsForRide(rideId: Long) =
//            repository.getAvailableSeatsForRide(rideId = rideId)

//    fun setRideFinished(rideId: Long): Boolean =
//            repository.changeRideStatus(rideId = rideId, status = RideStatus.FINISHED) == 1
//
//    fun decreaseSeatsOffered(rideId: Long, seatsToMinus: Int): Ride {
//        val ride = findById(rideId)
//        if (ride.getAvailableSeats().minus(seatsToMinus) >= 0)
//            ride.copy(totalSeatsOffered = ride.totalSeatsOffered - seatsToMinus)
//        else throw NotEnoughSeatsToDeleteException(rideId, seatsToMinus, ride.getAvailableSeats())
//        return ride
//    }
//
//    fun getAllRidesFromLocation(location: String) =
//            repository.findAllByFromLocationName(fromLocationName = location)
//
//    fun getAllRidesForDestination(destination: String) =
//            repository.findAllByDestinationName(destination = destination)
}

