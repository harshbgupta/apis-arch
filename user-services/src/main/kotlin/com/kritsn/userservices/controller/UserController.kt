package com.kritsn.userservices.controller

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
}