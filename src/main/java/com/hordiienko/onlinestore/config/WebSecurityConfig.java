package com.hordiienko.onlinestore.config;

import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .antMatcher("/v1/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasAnyRole("ADMIN", "USER")
                )
//                .antMatcher("/admin_page")
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().hasRole("ADMIN")
//                )
                .httpBasic(withDefaults());
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
//    @Bean
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf()
//                .disable()
//                .authorizeRequests()
////                .antMatchers(HttpMethod.GET, "/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
////                .antMatchers(HttpMethod.POST, "/**").hasAnyRole( "ROLE_USER")
////                .antMatchers(HttpMethod.POST, "/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
////                .antMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
//                .antMatchers("/admin_page").hasRole("ROLE_ADMIN")
//                .antMatchers("/online_shop").hasRole("ROLE_USER")
////                .antMatchers("/", "/resources/**").permitAll()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/");
//    }

}