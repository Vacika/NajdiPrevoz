package com.project.najdiprevoz.api

import com.project.najdiprevoz.enums.NotificationAction
import com.project.najdiprevoz.services.NotificationManagementService
import com.project.najdiprevoz.services.NotificationService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/notifications")
class NotificationController (private val service: NotificationManagementService){

    @GetMapping("/all")
    fun getMyNotifications() = service.getMyNotifications("email2@email.com") //TODO: Switch with principal.name

    @GetMapping
    fun getUnreadNotifications() = service.getUnreadNotifications("email2@email.com") //TODO: Switch with principal.name

    @PutMapping("/{notificationId}/action")
    fun takeAction(@PathVariable("notificationId") notificationId: Long, @RequestBody action: String) = service.takeAction(notificationId, NotificationAction.valueOf(action))
}