package com.project.najdiprevoz.api

import com.project.najdiprevoz.services.UserService
import com.project.najdiprevoz.web.request.create.CreateUserRequest
import com.project.najdiprevoz.web.request.edit.ChangeProfilePhotoRequest
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/members")
class UserController(private val userService: UserService) {

    @GetMapping("/{userId}")
    fun findUserById(@PathVariable("userId") userId: Long) =
            userService.findUserById(userId)

    @GetMapping
    fun findUserByUsername(username: String) =
            userService.findUserByUsername(username)

    @PutMapping
    fun createUser(@RequestBody request: CreateUserRequest) =
            userService.createNewUser(request)

    @GetMapping("/edit/profile-photo")
    fun editProfilePhoto(request: ChangeProfilePhotoRequest) =
            userService.editProfilePhoto(request)

    @PutMapping("/edit/password")
    fun changePassword(password: String, principal: Principal) =
            userService.changePassword(password, principal.name)
}