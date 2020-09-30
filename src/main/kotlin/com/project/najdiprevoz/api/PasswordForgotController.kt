package com.project.najdiprevoz.api

import com.project.najdiprevoz.services.PasswordForgotService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/forgot-password")
class PasswordForgotController(private val passwordForgotService: PasswordForgotService) {


    @PostMapping
    fun createResetTokenForUser(@RequestBody username: String) =
            passwordForgotService.createResetTokenForUser(username)
}
