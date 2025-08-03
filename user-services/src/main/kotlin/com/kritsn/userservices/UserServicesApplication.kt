package com.kritsn.userservices

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(scanBasePackages = ["com.kritsn.userservices", "com.kritsn.lib"])
@EnableDiscoveryClient
class UserServicesApplication

fun main(args: Array<String>) {
    runApplication<UserServicesApplication>(*args)
}
