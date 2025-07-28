package com.kritsn.gateway.filter

import io.jsonwebtoken.*
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*


@Component
class GatewaySecurityFilter : OncePerRequestFilter() {
    @Value("\${jwt.secret}")
    private val secretKey: String? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI

        // Allow public URLs without JWT check
        if (PUBLIC_URLS.stream().anyMatch { prefix: String? -> path.startsWith(prefix!!) }) {
            filterChain.doFilter(request, response)
            return
        }

        // Check for Authorization header
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Missing or invalid Authorization header")
            return
        }

        val token = authHeader.substring(7)

        val subject: String
        try {
            val claims = Jwts.parser()
                .setSigningKey(secretKey!!.toByteArray())
                .parseClaimsJws(token)
                .getBody()

            subject = claims.subject // Usually the username or userId
        } catch (e: JwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Invalid JWT token")
            return
        }

        // Wrap request to add header for downstream service
        val wrapper: HttpServletRequest = object : HttpServletRequestWrapper(request) {
            override fun getHeader(name: String?): String? {
                if ("X-User-Id".equals(name, ignoreCase = true)) {
                    return subject
                }
                return super.getHeader(name)
            }

            override fun getHeaders(name: String?): Enumeration<String?>? {
                if ("X-User-Id".equals(name, ignoreCase = true)) {
                    return Collections.enumeration(listOf(subject))
                }
                return super.getHeaders(name)
            }
        }

        filterChain.doFilter(wrapper, response)
    }

    companion object {
        // List of public URLs that should not require authentication
        private val PUBLIC_URLS = mutableListOf<String?>("/login", "/register", "/health")
    }
}