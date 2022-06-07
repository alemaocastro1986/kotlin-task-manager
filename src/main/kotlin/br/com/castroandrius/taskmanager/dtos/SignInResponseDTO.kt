package br.com.castroandrius.taskmanager.dtos

data class SignInResponseDTO(val name: String, val email: String, val token: String = "")
