package com.apapedia.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(basic -> basic.disable());
        return http.build();
    }

    // public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    // @Autowired
    // private UserDetailsService userDetailsService;

    // @Autowired
    // public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
    //     auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    // }

}



