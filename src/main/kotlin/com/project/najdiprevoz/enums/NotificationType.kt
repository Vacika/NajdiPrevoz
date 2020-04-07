package com.project.najdiprevoz.enums


enum class NotificationType(private val type: String) {
    REQUEST_SENT("REQUEST_SENT"),
    REQUEST_DENIED("REQUEST_DENIED"),
    REQUEST_APPROVED("REQUEST_APPROVED"),
    REQUEST_CANCELLED("REQUEST_CANCELLED"),
    RIDE_CANCELLED("RIDE_CANCELLED"),
    RATING_SUBMITTED("RATING_GIVEN"),
    REQUEST_EXPIRED("Request expired")
}