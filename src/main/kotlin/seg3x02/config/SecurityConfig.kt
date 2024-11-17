package seg3x02.tempconverterapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { it.disable() }
                .authorizeHttpRequests { auth -> auth.anyRequest().authenticated() }
                .httpBasic {}

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user1 =
                User.builder()
                        .username("user1")
                        .password(passwordEncoder().encode("pass1"))
                        .roles("USER")
                        .build()

        val user2 =
                User.builder()
                        .username("user2")
                        .password(passwordEncoder().encode("pass2"))
                        .roles("USER")
                        .build()

        return InMemoryUserDetailsManager(user1, user2)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
