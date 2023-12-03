package com.apapedia.frontend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(new AntPathRequestMatcher("/assets/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/bootstrap/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/img/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/webfonts/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/validate-ticket")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/logout-sso")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/logout")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .anyRequest().authenticated()
                ) 
                .formLogin((form) -> form
                        .loginPage("/login-sso")
                        .permitAll()
                        .defaultSuccessUrl("/")
                );
                // .logout((logout) -> logout
                //         .logoutRequestMatcher(new AntPathRequestMatcher("/logout-sso"))
                //         .logoutSuccessUrl("/")
                // );
        return http.build();

    }

}
