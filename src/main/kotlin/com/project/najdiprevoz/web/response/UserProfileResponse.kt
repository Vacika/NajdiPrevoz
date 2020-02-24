package com.project.najdiprevoz.web.response

import com.project.najdiprevoz.domain.Rating

class UserProfileResponse(val id: Long,
                          val firstName: String,
                          val lastName: String,
                          val profilePhoto: String?,
                          val username: String,
                          val phoneNumber: String?,
                          val gender: String,
                          val ratings: List<Rating>?,
                          val averageRating: Double)