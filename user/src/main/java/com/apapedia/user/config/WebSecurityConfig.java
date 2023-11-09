package com.apapedia.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(login -> login
                        .loginPage("/login").permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login").permitAll());
        return http.build();
    }

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Qualifier("sellerServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

}


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


// @Configuration
// @EnableWebSecurity
// // public class WebSecurityConfig extends WebSecurityConfiguration {

// //      SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

// //         http
// //                 .authorizeRequests(authorizeRequests ->
// //                         authorizeRequests
// //                                 .requestMatchers("/", "/signup").permitAll()
// //                                 .requestMatchers("/seller/**").hasAnyAuthority("seller")
// //                                 .anyRequest().authenticated()
// //                 )
// //                 .formLogin(login -> login
// //                     .loginPage("/login")
// //                     .defaultSuccessUrl("/")
// //                     .permitAll()
// //                 )
// //                 .logout(logout -> logout
// //                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// //                     .logoutSuccessUrl("/login")
// //                     .permitAll()
// //                 );
    
// //         return http.build();
// //     }

// //     // @Bean
// //     //  AuthenticationManager authManager(HttpSecurity http) throws Exception {
// //     //     return http.getSharedObject(AuthenticationManagerBuilder.class)
// //     //         .authenticationProvider(authProvider())
// //     //         .build();
// //     // }

// //     // @Bean
// //     // public BCryptPasswordEncoder passwordEncoder() {
// //     //     return new BCryptPasswordEncoder();
// //     // }


// //     @Autowired
// //     private UserDetailsService userDetailsService;

// //     // @Autowired
// //     // public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
// //     //     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
// //     // }

// //     @Bean
// //      AuthenticationProvider authProvider() {
// //         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
// //         authProvider.setUserDetailsService(userDetailsService);
// //         authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
// //         return authProvider;
// //     }

// // }
// // @Configuration
// // @EnableWebSecurity
// public class WebSecurityConfig  {

//     @Bean
//      SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//         http
//                 .authorizeRequests(authorizeRequests ->
//                         authorizeRequests
//                                 .requestMatchers("/", "/signup").permitAll()
//                                 .requestMatchers("/seller/**").hasRole("SELLER")
//                                 .anyRequest().authenticated()
//                 )
//                 .formLogin(login -> login
//                     .loginPage("/login")
//                     .defaultSuccessUrl("/")
//                     .permitAll()
//                 )
//                 .logout(logout -> logout
//                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                     .logoutSuccessUrl("/login")
//                     .permitAll()
//                 );
    
//         return http.build();
//     }

//     @Bean
//      AuthenticationManager authManager(HttpSecurity http) throws Exception {
//         return http.getSharedObject(AuthenticationManagerBuilder.class)
//             .authenticationProvider(authProvider())
//             .build();
//     }

//     // @Bean
//     // public BCryptPasswordEncoder passwordEncoder() {
//     //     return new BCryptPasswordEncoder();
//     // }


//     @Autowired
//     private UserDetailsService userDetailsService;

//     // @Autowired
//     // public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
//     //     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//     // }

//     @Bean
//      DaoAuthenticationProvider authProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(userDetailsService);
//         authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//         return authProvider;
//     }

// }

