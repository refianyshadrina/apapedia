package com.apapedia.user.config.temp;
// package com.apapedia.user.config;


// import com.apapedia.user.model.Customer;
// import com.fasterxml.jackson.annotation.JsonIgnore;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;

// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.List;
// import java.util.Objects;
// import java.util.UUID;
// import java.util.stream.Collectors;

// public class CustomerDetailsImpl implements UserDetails {

//     private static final long serialVersionUID = 1L;

//     private UUID uuid;

//     private String username;

//     private String email;

//     @JsonIgnore
//     private String password;

//     private Collection<? extends GrantedAuthority> authorities;

//     public CustomerDetailsImpl(UUID uuid, String username, String email, String password, List<GrantedAuthority> authorities) {
//         this.uuid = uuid;
//         this.username = username;
//         this.email = email;
//         this.password = password;
//         this.authorities = authorities;
//     }

//     public static CustomerDetailsImpl build(Customer customer) {
//         List<String> roles = new ArrayList<>();
//         roles.add(customer.getRole());
//         List<GrantedAuthority> authorities = roles.stream()
//                 .map(role -> new SimpleGrantedAuthority(role))
//                 .collect(Collectors.toList());
//         return new CustomerDetailsImpl(
//                 customer.getId(),
//                 customer.getUsername(),
//                 customer.getEmail(),
//                 customer.getPassword(),
//                 authorities
//         );
//     }

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return authorities;
//     }

//     public UUID getUuid(){
//         return uuid;
//     }
//     public String getEmail(){
//         return email;
//     }

//     @Override
//     public String getPassword() {
//         return password;
//     }

//     @Override
//     public String getUsername() {
//         return username;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o)
//             return true;
//         if (o == null || getClass() != o.getClass())
//             return false;
//         CustomerDetailsImpl user = (CustomerDetailsImpl) o;
//         return Objects.equals(uuid, user.uuid);
//     }

//     public String hasAuthority(String authorityToCheck) {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//         if (authentication != null && authentication.getAuthorities() != null) {
//             if (authentication.getAuthorities().stream()
//                 .anyMatch(auth -> auth.getAuthority().equals(authorityToCheck))) {
//                     return "true";
//                 }
//         }

//         return "false";
//     }

// }


