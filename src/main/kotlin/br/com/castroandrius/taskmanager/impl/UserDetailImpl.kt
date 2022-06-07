package br.com.castroandrius.taskmanager.impl

import br.com.castroandrius.taskmanager.entities.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailImpl(private val user: User): UserDetails{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf<GrantedAuthority>()

    override fun getPassword(): String  = user.password_hash

    override fun getUsername(): String  =user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}