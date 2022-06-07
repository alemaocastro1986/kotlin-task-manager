package br.com.castroandrius.taskmanager.entities

data class User(val id: Long, val name: String, val email: String, val password_hash: String) {
}