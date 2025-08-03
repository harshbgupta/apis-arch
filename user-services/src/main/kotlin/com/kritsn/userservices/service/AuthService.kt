package com.kritsn.userservices.service

import com.kritsn.lib.base.Response
import com.kritsn.lib.base.buildErrorResponse
import com.kritsn.lib.base.buildSuccessResponse
import com.kritsn.lib.jwt.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired val jwtUtil: JwtUtil) {

    fun handleGenerateToken(mobileNumber: String): Response<String> {
        try {
            val jwtToken = jwtUtil.generateToken(mobileNumber)
            return buildSuccessResponse(jwtToken)
        } catch (e: Exception) {
            e.printStackTrace()
            return buildErrorResponse(e.message)
        }
    }

    fun handleRefreshToken(tokenWithBearer: String?): Response<String> {
        try {
            //Refreshing token
            val refreshedJwtToken = jwtUtil.refreshToken(tokenWithBearer)
            return buildSuccessResponse(refreshedJwtToken)
        } catch (e: Exception) {
            e.printStackTrace()
            return buildErrorResponse(e.message)
        }
    }
}