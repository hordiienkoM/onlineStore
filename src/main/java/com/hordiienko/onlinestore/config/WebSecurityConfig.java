package com.hordiienko.onlinestore.config;

import com.hordiienko.onlinestore.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService());
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> {
                    auth.antMatchers("/registration").not().authenticated();
                    auth.antMatchers("/confirm_registration").not().authenticated();
                    auth.antMatchers("/").permitAll();
                    auth.antMatchers(HttpMethod.POST, "/v1/users").not().authenticated();
                    auth.antMatchers(HttpMethod.POST, "/v1/users/confirm").not().authenticated();
                    auth.antMatchers(HttpMethod.GET, "/v1/users/enable").not().authenticated();
                    auth.antMatchers("/login").permitAll();
                    auth.antMatchers("/online_shop", "/home").hasAnyRole("ADMIN", "USER");
                    auth.antMatchers("/admin_page").hasRole("ADMIN");
                    auth.antMatchers(HttpMethod.DELETE, "/v1/admin/users").hasRole("ADMIN");
                    auth.antMatchers("/v1/**").hasAnyRole("USER", "ADMIN");
                    auth.anyRequest().hasAnyRole("USER", "ADMIN");
                })
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/online_shop")
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/src/**");
    }
}