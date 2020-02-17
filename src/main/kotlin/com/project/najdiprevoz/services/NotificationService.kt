package com.project.najdiprevoz.services

import com.project.najdiprevoz.domain.*
import com.project.najdiprevoz.enums.RequestStatus
import com.project.najdiprevoz.repositories.NotificationRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class NotificationService(private val repository: NotificationRepository) {

    fun pushNotification(rideRequest: RideRequest) {
        var actionsAllowed: List<String> = listOf(Actions.MARK_AS_SEEN.name)
        var to: Member
        var from: Member
        val driver: Member = rideRequest.ride.driver
        val requester: Member = rideRequest.requester
        var notificationType: NotificationType = NotificationType.REQUEST_SENT
        when (rideRequest.status) {
            RequestStatus.APPROVED -> {
                actionsAllowed.plus(Actions.CANCEL.name)
                from = driver
                to = requester
                notificationType = NotificationType.REQUEST_APPROVED
            }
            RequestStatus.DENIED -> {
                from = driver
                to = requester
                notificationType = NotificationType.REQUEST_DENIED

            }
            RequestStatus.PENDING -> {
                actionsAllowed.plus(Actions.DENY.name).plus(Actions.APPROVE.name)
                from = requester
                to = driver
                notificationType = NotificationType.REQUEST_SENT
            }
            RequestStatus.CANCELLED -> {
                from = requester
                to = driver
                notificationType = NotificationType.REQUEST_CANCELLED
            }
            RequestStatus.RIDE_CANCELLED -> {
                from = driver
                to = requester
                notificationType = NotificationType.RIDE_CANCELLED
            }
        }
        repository.saveAndFlush(Notification(createdOn = ZonedDateTime.now(),
                rideRequest = rideRequest,
                actionsAvailable = actionsAllowed.joinToString(separator = ", "),
                seen = false,
                from = from,
                to = to,
                type = notificationType))
    }

    fun pushNotification(rideRequest: RideRequest, type: NotificationType) {
        var status = when (type) {
            NotificationType.RIDE_CANCELLED -> RequestStatus.RIDE_CANCELLED
            NotificationType.REQUEST_SENT -> RequestStatus.PENDING
            NotificationType.REQUEST_CANCELLED -> RequestStatus.CANCELLED
            NotificationType.REQUEST_APPROVED -> RequestStatus.APPROVED
            NotificationType.REQUEST_DENIED -> RequestStatus.DENIED
            NotificationType.RATING_SUBMITTED -> throw Exception("RATING_SUBMITTED notification type can not be used on ride request status change!")
        }
        rideRequest.status = status
        pushNotification(rideRequest)
    }

    fun pushRatingNotification(rating: Rating) = with(rating) {
        repository.saveAndFlush(
                Notification(
                        from = rating.getAuthor(),
                        to = rating.getDriver(),
                        type = NotificationType.RATING_SUBMITTED,
                        rideRequest = rating.rideRequest,
                        seen = false,
                        actionsAvailable = Actions.MARK_AS_SEEN.toString(),
                        createdOn = ZonedDateTime.now()
                )
        )
    }
}