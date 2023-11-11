package com.apapedia.user.config;

// import com.apapedia.user.config.jwt.AuthEntryPointJwt;
// import com.apapedia.user.config.jwt.AuthTokenFilter;
// import com.apapedia.user.config.jwt.JwtAuthFilter;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// public class WebSecurityConfig {
//     @Autowired
//     private JwtAuthFilter authFilter;

//     // @Bean
//     //authentication
//     @Autowired
//     private UserDetailsService userDetailsService;

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http.cors().and().csrf().disable()
//                 .authorizeHttpRequests()
//                 .requestMatchers("/", "/signup", "/login").permitAll()
//                 .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                 .requestMatchers("/api/register").permitAll()
//                 .requestMatchers("/api/login").permitAll()
//                 .requestMatchers(HttpMethod.POST, "/seller").hasAnyAuthority("seller")
//                 .and()
//                 .authorizeHttpRequests().requestMatchers("/seller")
//                 .authenticated().and()
//                 // .formLogin(login -> login
//                 //         .loginPage("/login").permitAll())
//                 .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                         .logoutSuccessUrl("/login").permitAll())
//                 .sessionManagement()
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 .and()
//                 .authenticationProvider(authenticationProvider())
//                 .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider(){
//         DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
//         authenticationProvider.setUserDetailsService(userDetailsService);
//         // authenticationProvider.setUserDetailsService(userDetailsService());
//         authenticationProvider.setPasswordEncoder(passwordEncoder());
//         return authenticationProvider;
//     }
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

// }



// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(
//         securedEnabled = true,
//         jsr250Enabled = true,
//         prePostEnabled = true)
// public class WebSecurityConfig{
//     @Autowired
//     private SellerDetailsServiceImpl userDetailsService;

//     @Autowired
//     private AuthEntryPointJwt unauthorizedHandler;

//     @Bean
//     public AuthTokenFilter authenticationJwtTokenFilter(){
//         return new AuthTokenFilter();
//     }

//     // @Bean
//     public AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//         return auth.build();
//     }



//     // @Bean
//     // @Override
//     // public AuthenticationManager authenticationManagerBean() throws Exception {
//     //     return super.authenticationManagerBean();
//     // }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//         return authenticationConfiguration.getAuthenticationManager();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     // @Override
//     // protected void configure(HttpSecurity http) throws Exception {
//     //     http.cors().and().csrf().disable()
//     //             .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//     //             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//     //             .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//     //             .antMatchers("/api/dash/**").permitAll()
//     //             .anyRequest().authenticated();
//     //     http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//     // }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.cors().and().csrf().disable()
//             .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//             .authorizeRequests().requestMatchers("/api/signin").permitAll()
//             .requestMatchers("/").permitAll()
//             .requestMatchers("/signup").permitAll()
//             .anyRequest().authenticated();
//         http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }

// }



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
        http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/signup").permitAll()
                .requestMatchers("/api/register").permitAll()
                .requestMatchers("/api/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(login -> login
                        .loginPage("/login").permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login").permitAll());
        return http.build();
    }

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // @Qualifier("sellerServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

}