package br.com.castroandrius.taskmanager.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JWTUtils {
    private val secret = "MinhaChaveSuperSecreta";

    fun generateToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
            .compact()
    }

    fun validate(token: String): Boolean{
        val claims = getClaimsToken(token)
        if(claims !== null){
            val userId  = claims.subject
            if(!userId.isNullOrEmpty() && userId.isNotBlank()){
                return true
            }
        }

        return false
    }

    private fun getClaimsToken(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        }catch (ex: Exception){
            null
        }
    }

    fun getUserId(token: String): String?{
        val claims = getClaimsToken(token)
        return claims?.subject
    }

}