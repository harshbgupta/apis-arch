package com.kritsn.userservices.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController {

    @GetMapping("/test")
    fun test(): String {
        return "Login Controller Test Success"
    }
}