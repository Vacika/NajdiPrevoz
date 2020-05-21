package com.project.najdiprevoz.services

import com.project.najdiprevoz.domain.RideRequest
import com.project.najdiprevoz.enums.RideRequestStatus
import com.project.najdiprevoz.enums.RideStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.Modifying
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CronJobService(private val rideRequestService: RideRequestService,
                     private val tripService: TripService,
                     private val ratingService: RatingService) {

    val logger: Logger = LoggerFactory.getLogger(CronJobService::class.java)

    @Scheduled(fixedRate = 10000)
    @Modifying
    @Transactional
    fun updateRidesAndRequestsJob() {
        updateRideCron()
        logger.info("[CRONJOB] Updating EXPIRED and RIDE_CANCELLED ride requests..")
        updateRideRequestCron()
    }

    private fun updateRideRequestCron() {
        val allRideRequests = rideRequestService.getAll()

        allRideRequests
                .filter { it.status == RideRequestStatus.PENDING && it.ride.status == RideStatus.FINISHED }
                .forEach { changeRequestToExpired(it) }

        allRideRequests
                .filter {
                    it.status == RideRequestStatus.APPROVED && it.ride.status == RideStatus.FINISHED && !checkIfHasRatingAllowedNotification(
                            it)
                }
                .forEach { sendRatingNotification(it) }

    }

    private fun sendRatingNotification(it: RideRequest) {
        ratingService.pushRatingAllowedNotification(it)
    }

    private fun changeRequestToExpired(rideRequest: RideRequest) =
            rideRequestService.rideRequestCronJob(rideRequest)

    private fun updateRideCron() =
            tripService.checkForFinishedTripsCronJob()

    private fun checkIfHasRatingAllowedNotification(rideRequest: RideRequest): Boolean =
            ratingService.checkIfHasRatingAllowedNotification(rideRequest)
}