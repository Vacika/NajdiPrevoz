package com.project.najdiprevoz.api

import com.project.najdiprevoz.services.MemberService
import com.project.najdiprevoz.web.request.create.CreateMemberRequest
import com.project.najdiprevoz.web.request.edit.ChangeProfilePhotoRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/members")
class MemberController(private val memberService: MemberService) {

    @GetMapping("/{memberId}")
    fun findMemberById(@PathVariable("memberId") memberId: Long) =
            memberService.findMemberById(memberId)

    @PutMapping
    fun createMember(@RequestBody createMemberRequest: CreateMemberRequest) =
            memberService.createNewUser(createMemberRequest)

    @GetMapping("/edit/profile-photo")
    fun editProfilePhoto(changeProfilePhotoRequest: ChangeProfilePhotoRequest) =
            memberService.editProfilePhoto(changeProfilePhotoRequest)
}