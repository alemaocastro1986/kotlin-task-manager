package br.com.castroandrius.taskmanager.controllers

import br.com.castroandrius.taskmanager.dtos.ErrorDTO
import br.com.castroandrius.taskmanager.dtos.SignInDTO
import br.com.castroandrius.taskmanager.dtos.SignInResponseDTO
import br.com.castroandrius.taskmanager.utils.JWTUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/login")
class LoginController {
    private val EMAIL_TEST = "johndoe@teste.com"
    private val PASSWORD_TEST = "123456"
    private val USER_ID = 1

    @PostMapping
    fun signIn(@RequestBody dto: SignInDTO): ResponseEntity<Any> {
        try {
            val isValidEmail = dto.email.isNotEmpty() || dto.email.isNotBlank()
            val isValidPassword = dto.password.isNotBlank() || dto.password.isNotEmpty()
            val isValidInputs = dto.email == EMAIL_TEST && dto.password == PASSWORD_TEST
            if(isValidEmail.not() || isValidPassword.not() || isValidInputs.not()){
                return ResponseEntity(
                    ErrorDTO(status = HttpStatus.BAD_REQUEST.value(), "Email or password invalid."),
                    HttpStatus.BAD_REQUEST
                )
            }
            val token  = JWTUtils().generateToken(USER_ID.toString())
            val userTest  = SignInResponseDTO("John Doe", EMAIL_TEST, token)
            return ResponseEntity(userTest, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(
                ErrorDTO(status = HttpStatus.INTERNAL_SERVER_ERROR.value(), "Não foi possivel efetuar o login"),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }
}