package com.kritsn.userservices.controller

import com.kritsn.lib.base.BaseResponse
import com.kritsn.lib.base.buildSuccessResponse
import org.apache.kafka.common.errors.ResourceNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @GetMapping("/test")
    fun test(): String {
        return "User Controller Test Success"
    }
    @GetMapping("/dummy")
    private fun dummyApi(): BaseResponse {
        throw ResourceNotFoundException("Dummy Exception")
        return buildSuccessResponse()
    }
}