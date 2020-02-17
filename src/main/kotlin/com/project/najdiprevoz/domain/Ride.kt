package com.project.najdiprevoz.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.project.najdiprevoz.enums.RequestStatus
import com.project.najdiprevoz.enums.RideStatus
import java.time.ZonedDateTime
import javax.persistence.*

//TODO: Implement Builder Pattern

@Entity
@Table(name = "rides")
data class Ride(
        @Column(name = "created_on")
        val createdOn: ZonedDateTime,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "from_location", referencedColumnName = "id")
        val fromLocation: City,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "to_location", referencedColumnName = "id")
        val destination: City,

        @Column(name = "departure_time")
        val departureTime: ZonedDateTime,

        @Column(name = "total_seats_offered")
        val totalSeatsOffered: Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "driver_id", nullable = false)
        val driver: Member,

        @Column(name = "price_per_head")
        val pricePerHead: Int,

        @Column(name = "description")
        val additionalDescription: String?,

        @JsonManagedReference
        @OneToMany(mappedBy = "ride", fetch = FetchType.EAGER, cascade = [CascadeType.ALL]) //TODO: Change this to LAZY OR EAGER?
        val rideRequests: List<RideRequest> = listOf(),

        @JsonManagedReference
        @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val rating: List<Rating> = listOf(),

        @Enumerated(EnumType.STRING)
        val status: RideStatus

) : BaseEntity<Long>() {
    fun getAvailableSeats(): Int {
        if (this.rideRequests != null) {
            return this.totalSeatsOffered - this.rideRequests.filter { it.status == RequestStatus.APPROVED }.size
        }
        return this.totalSeatsOffered
    }

    fun canApproveRideRequest(): Boolean = this.getAvailableSeats() > 0

    fun isFinished(): Boolean = this.status == RideStatus.FINISHED

    fun setStatus(status: RideStatus) = this.copy(status = status)

    @Override
    override fun toString(): String {
        return ""
    }

}