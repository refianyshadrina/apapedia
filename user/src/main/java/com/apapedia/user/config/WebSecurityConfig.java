package com.apapedia.user.config;

import com.apapedia.user.config.jwt.AuthEntryPointJwt;
import com.apapedia.user.config.jwt.AuthTokenFilter;
import com.apapedia.user.config.userdetails.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
// (securedEnabled = true,
// jsr250Enabled = true,
// prePostEnabled = true) // by default
public class WebSecurityConfig { 
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/", "/signup", "/login", "/api/register", "/api/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/logout").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                                .requestMatchers("/seller/**").permitAll()
                                .requestMatchers("/customer").permitAll()
                                // .requestMatchers("/seller/**").hasAnyAuthority("seller")
                                // .requestMatchers("/customer").hasAnyAuthority("customer")
                                .requestMatchers("/api/customer/**").permitAll()
                                .anyRequest().authenticated()
                );

        http
                .logout(logout -> logout
                        .logoutUrl("/logout") 
                        .addLogoutHandler(cookieClearingLogoutHandler())
                        .logoutSuccessHandler(logoutSuccessHandler()) 
                        .logoutSuccessUrl("/login")
                        .permitAll());

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public LogoutHandler cookieClearingLogoutHandler() {
        return (request, response, authentication) -> {
            // kalo yang logout customer gimanaaaaaa :(
            List<String> paths = Arrays.asList("/", "/seller");

            for (String path : paths) {
                Cookie cookie = new Cookie("jwtToken", null);
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true);
                cookie.setPath(path);
                response.addCookie(cookie);
            }
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    public static class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException {
            response.sendRedirect("/"); 
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().flush();
        }
    }
}


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @Configuration
// @EnableWebSecurity
// public class WebSecurityConfig {
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.cors().and().csrf().disable()
//                 .authorizeHttpRequests()
//                 .requestMatchers("/").permitAll()
//                 .requestMatchers("/css/**").permitAll()
//                 .requestMatchers("/js/**").permitAll()
//                 .requestMatchers("/signup").permitAll()
//                 .requestMatchers("/api/register").permitAll()
//                 .requestMatchers("/api/login").permitAll()
//                 .anyRequest().authenticated()
//                 .and()
//                 .formLogin(login -> login
//                         .loginPage("/login").permitAll())
//                 .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                         .logoutSuccessUrl("/login").permitAll());
//         return http.build();
//     }

//     public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//     // @Qualifier("sellerServiceImpl")
//     @Autowired
//     private UserDetailsService userDetailsService;

//     @Autowired
//     public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
//         auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
//     }

// }