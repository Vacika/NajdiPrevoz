package com.project.najdiprevoz.web.request.edit

import java.time.ZonedDateTime

class EditTripRequest(val fromLocation: String,
                      val toLocation: String,
                      val departureTime: ZonedDateTime,
                      val description: String,
                      val pricePerHead: Int)