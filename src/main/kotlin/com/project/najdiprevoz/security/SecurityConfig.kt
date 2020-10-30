package com.project.najdiprevoz.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.najdiprevoz.services.passwordEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private val service: UserDetailsServiceImpl,

                     private val objectMapper: ObjectMapper) : WebSecurityConfigurerAdapter() {

    @Bean
    fun jwtFilter(): JwtRequestFilter {
        return JwtRequestFilter()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder())
    }

//    @Autowired
//    @Throws(Exception::class)
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService<UserDetailsService>(userDetailsService()).passwordEncoder(passwordEncoder())
//    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(service)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }


    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate**").permitAll()
                .antMatchers("/api/forgot-password**").permitAll()
                .antMatchers("/api/reset-password**").permitAll()
                .antMatchers("/api/trips-list/**").permitAll()
                .antMatchers("/api/cities").permitAll()
                .antMatchers("/api/users/register").permitAll()
                .antMatchers("/api/users/activate").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(NoPopupBasicAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)

//                .formLogin()
//                .successHandler(::loginSuccessHandler)
//                .failureHandler(::loginFailureHandler)
//                .and()
//                .logout()
//                .logoutUrl("/api/logout")
//                .logoutSuccessHandler(::logoutSuccessHandler)
//                .and()

//                .invalidateHttpSession(true)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(NoPopupBasicAuthenticationEntryPoint())
    }

    private fun loginSuccessHandler(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        response.status = HttpStatus.OK.value()
        objectMapper.writeValue(response.writer, authentication.principal)
    }

//    private fun loginFailureHandler(request: HttpServletRequest, response: HttpServletResponse, e: AuthenticationException) {
//        response.status = HttpStatus.UNAUTHORIZED.value()
//        if(e.message == "USER_NOT_ACTIVATED"){
//            response.status = HttpStatus.FORBIDDEN.value()
//        }
//        response.outputStream.println(objectMapper.writeValueAsString(e.message))
//    }
//
    private fun logoutSuccessHandler(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        response.status = HttpStatus.OK.value()
        objectMapper.writeValue(response.writer, "Successfully logged out!!");
    }
}
