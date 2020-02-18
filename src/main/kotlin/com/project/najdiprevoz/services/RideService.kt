package com.project.najdiprevoz.services

import com.project.najdiprevoz.domain.NotificationType
import com.project.najdiprevoz.domain.Ride
import com.project.najdiprevoz.domain.RideRequest
import com.project.najdiprevoz.domain.User
import com.project.najdiprevoz.enums.RideStatus
import com.project.najdiprevoz.exceptions.NotEnoughSeatsToDeleteException
import com.project.najdiprevoz.exceptions.RideNotFoundException
import com.project.najdiprevoz.repositories.RideRepository
import com.project.najdiprevoz.web.request.create.CreateRideRequest
import com.project.najdiprevoz.web.request.edit.EditRideRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class RideService(private val repository: RideRepository,
                  private val userService: UserService,
                  private val cityService: CityService,
                  private val notificationService: NotificationService) {

    val logger: Logger = LoggerFactory.getLogger(RideService::class.java)

    fun findAllActiveRides(): List<Ride> =
            repository.findAllByStatus(RideStatus.ACTIVE)

    fun findAllActiveRidesWithAvailableSeats() =
            findAllActiveRides().filter { it.getAvailableSeats() > 0 }

    fun createNewRide(createRideRequest: CreateRideRequest) =
            repository.save(createRideObject(createRideRequest = createRideRequest))

    fun getPastRidesForUser(userId: Long) =
            repository.findAllByDriverIdAndStatus(driverId = userId, status = RideStatus.FINISHED)

    fun setRideFinished(rideId: Long): Boolean =
            repository.changeRideStatus(rideId = rideId, status = RideStatus.FINISHED) == 1

    fun deleteRide(rideId: Long) {
        val ride = findById(rideId)
        ride.rideRequests.forEach { pushNotification(it, NotificationType.RIDE_CANCELLED) }
        repository.changeRideStatus(rideId, RideStatus.CANCELLED)
        logger.info("[RideService - DELETE RIDE] Ride with id $rideId successfully deleted!")
    }

    fun findById(id: Long): Ride =
            repository.findById(id).orElseThrow { RideNotFoundException("Ride with id $id was not found") }

    fun findAvailableSeatsForRide(rideId: Long) =
            repository.getAvailableSeatsForRide(rideId = rideId)

    fun findAllRidesForUser(user: User) =
            repository.findAllByDriver(driver = user)

    private fun createRideObject(createRideRequest: CreateRideRequest) = with(createRideRequest) {
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

    fun editRide(rideId: Long, editRideRequest: EditRideRequest) = with(editRideRequest) {
        val ride = findById(rideId)
        ride.copy(fromLocation = cityService.findByName(fromLocation),
                departureTime = departureTime,
                destination = cityService.findByName(toLocation),
                additionalDescription = description,
                pricePerHead = pricePerHead)
        repository.save(ride)
    }

    fun decreaseSeatsOffered(rideId: Long, seatsToMinus: Int): Ride {
        val ride = findById(rideId)
        if (ride.getAvailableSeats().minus(seatsToMinus) >= 0)
            ride.copy(totalSeatsOffered = ride.totalSeatsOffered - seatsToMinus)
        else throw NotEnoughSeatsToDeleteException(rideId, seatsToMinus, ride.getAvailableSeats())
        return ride
    }

    fun checkForFinishedRidesTask() {
        logger.info("[CRONJOB] Checking for finished rides..")
        logger.info("[CRONJOB] Updated [" + repository.updateRidesCron(ZonedDateTime.now()) + "] rides.")
    }

    fun getAllRidesFromLocation(location: String) =
            repository.findAllByFromLocationName(fromLocationName = location)

    fun getAllRidesForDestination(destination: String) =
            repository.findAllByDestinationName(destination = destination)

    private fun pushNotification(req: RideRequest, type: NotificationType) {
        notificationService.pushRequestStatusChangeNotification(req, type)
    }
}

