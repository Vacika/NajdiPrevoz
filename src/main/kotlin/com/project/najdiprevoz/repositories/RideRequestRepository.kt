package com.project.najdiprevoz.repositories

import com.project.najdiprevoz.domain.RideRequest
import com.project.najdiprevoz.enums.RideRequestStatus
import com.project.najdiprevoz.enums.RideStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface RideRequestRepository : JpaRepository<RideRequest, Long>, JpaSpecificationExecutor<RideRequest> {
    fun findAllByRideId(rideId: Long): List<RideRequest>

    fun findAllByRequesterUsername(username: String): List<RideRequest>

    @Query("SELECT r from RideRequest r where r.ride.driver.username = :username")
    fun findReceivedRequests(@Param("username") username:String): List<RideRequest>

    @Query("""
        SELECT rd from RideRequest rd 
        JOIN Ride r 
        ON r = rd.ride
        WHERE r.id = :rideId
        AND rd.status = 'APPROVED'
    """)
    fun findApprovedRequestsForRide(@Param("rideId") rideId: Long): List<RideRequest>

    @Transactional
    @Modifying
    @Query("""
        UPDATE RideRequest r
        SET r.status = :status
        WHERE r.id = :requestId
    """)
    fun updateRideRequestStatus(@Param("requestId") requestId: Long, @Param("status") status: RideRequestStatus): Int


    @Query("""
        SELECT r.ride.status
        FROM RideRequest r 
        WHERE r.id = :rideRequestId
    """)
    fun getRideStatus(@Param("rideRequestId") rideRequestId: Long): RideStatus


    @Modifying
    @Transactional
    @Query("""UPDATE RideRequest r set r.status='RIDE_CANCELLED' where r.status='PENDING' and r.ride.status='FINISHED'""")
    fun updateRideRequestsCron(): Int

    fun findByRideIdAndRequester_Username(rideId: Long, username: String): Optional<RideRequest>


    fun findAllByRequester_UsernameAndStatus(username: String, status: RideRequestStatus): List<RideRequest>
}