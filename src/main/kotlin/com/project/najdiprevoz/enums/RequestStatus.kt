package com.project.najdiprevoz.enums

enum class RequestStatus(private val status: String) {
    APPROVED("Approved"), DENIED("Denied"), PENDING("Pending"), CANCELLED("Cancelled"), RIDE_CANCELLED("Ride Cancelled")
}
