package com.project.najdiprevoz.services

import com.project.najdiprevoz.domain.RideRequest
import com.project.najdiprevoz.enums.RequestStatus
import com.project.najdiprevoz.repositories.RideRequestRepository
import com.project.najdiprevoz.web.request.create.CreateRequestForTrip
import com.project.najdiprevoz.web.response.RideRequestResponse
import com.project.najdiprevoz.web.response.UserShortResponse
import javassist.NotFoundException
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class RideRequestService(private val repository: RideRequestRepository,
                         private val tripService: TripService,
                         private val userService: UserService,
                         private val notificationService: NotificationService) {

    fun findRequestById(id: Long): RideRequestResponse = convertToRideRequestResponse(findById(id))

    fun findById(id: Long): RideRequest =
            repository.findById(id)
                    .orElseThrow { NotFoundException("Ride request not found!") }

    fun getAllRequestsByTripId(rideId: Long): List<RideRequestResponse> =
            repository.findAllByRideId(rideId).map { convertToRideRequestResponse(it) }

    fun getAllRequestsForUser(username: String) =
            repository.findAllByRequesterUsername(username = username)

    fun getAll(): List<RideRequest> =
            repository.findAll()

    fun getRequestsForRideByStatus(rideId: Long, status: RequestStatus): List<RideRequestResponse> =
            getAll()
                    .filter { it.ride.id == rideId && it.status == status }
                    .map { convertToRideRequestResponse(it) }

    fun changeStatusByRideRequestId(id: Long, newStatus: RequestStatus, notificationId: Long) {
        updateStatusIfPossible(requestId = id, previousStatus = findById(id).status, newStatus = newStatus)
        notificationService.markAsSeen(notificationId) // mark previous notification as SEEN
    }

    fun addNewRideRequest(createRideRequestForTrip: CreateRequestForTrip, username: String) = with(createRideRequestForTrip) {
        pushNotification(repository.save(RideRequest(
                status = RequestStatus.PENDING,
                ride = tripService.findById(rideId),
                createdOn = ZonedDateTime.now(),
                requester = userService.findUserByUsername(username))))
    }

    private fun updateStatusIfPossible(requestId: Long, previousStatus: RequestStatus, newStatus: RequestStatus) {
        if (changeStatusActionAllowed(previousStatus, newStatus))
            repository.updateRideRequestStatus(requestId = requestId, status = newStatus)
        pushNotification(findById(requestId))
    }

    private fun pushNotification(rideRequest: RideRequest) {
        notificationService.pushRequestStatusChangeNotification(rideRequest = rideRequest)
    }


    private fun changeStatusActionAllowed(previousStatus: RequestStatus, nextStatus: RequestStatus): Boolean {
        if (previousStatus != nextStatus) {
            return when (previousStatus) {
                RequestStatus.APPROVED -> nextStatus == RequestStatus.CANCELLED
                RequestStatus.PENDING -> true
                RequestStatus.CANCELLED -> false
                RequestStatus.DENIED -> false
                RequestStatus.RIDE_CANCELLED -> true
                RequestStatus.EXPIRED -> false
            }
        }
        return false
    }

    private fun convertToRideRequestResponse(rr: RideRequest): RideRequestResponse = rr.mapToRideRequestResponse()

    fun rideRequestCronJob(rideRequest: RideRequest, status: RequestStatus) {
        val request = findById(rideRequest.id)
        request.status = status
        repository.save(request)
        pushNotification(request)
    }
}