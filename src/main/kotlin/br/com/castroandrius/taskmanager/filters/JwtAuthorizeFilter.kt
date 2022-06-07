package br.com.castroandrius.taskmanager.filters

import br.com.castroandrius.taskmanager.authorization
import br.com.castroandrius.taskmanager.bearer
import br.com.castroandrius.taskmanager.entities.User
import br.com.castroandrius.taskmanager.impl.UserDetailImpl
import br.com.castroandrius.taskmanager.utils.JWTUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthorizeFilter(authenticateManager: AuthenticationManager, val jwtUtils: JWTUtils) :
    BasicAuthenticationFilter(authenticateManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader(authorization)
        if(authorizationHeader != null && authorizationHeader.startsWith(bearer)){
            val authorize = getAuthentication(authorizationHeader);
            SecurityContextHolder.getContext().authentication = authorize
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(authorizationHeader: String): UsernamePasswordAuthenticationToken {
        val token  = authorizationHeader.substring(bearer.count() + 1)
        if(jwtUtils.validate(token)){

            val userId = jwtUtils.getUserId(token)
            if (userId != null && userId.isNotEmpty() && userId.isNotBlank()) {
                val user = User(userId.toLong(), "John Doe", "johndoe@test.com", "123456")
                val userImpl = UserDetailImpl(user)
                return UsernamePasswordAuthenticationToken(userImpl,null, userImpl.authorities)
            }
        }

        throw UsernameNotFoundException("Token invalid")

    }
}