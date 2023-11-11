package com.apapedia.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class SellerDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SellerDb sellerDb;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerDb.findByUsername(username);
        if (seller == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        return SellerDetailsImpl.build(seller);
    }

}

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.apapedia.user.model.Seller;
// import com.apapedia.user.model.UserModel;
// import com.apapedia.user.repository.SellerDb;

// import java.util.HashSet;
// import java.util.Set;

// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {
//     @Autowired
//     private SellerDb userDb;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         System.out.println(username);
//         Seller user = userDb.findByUsername(username);
//         System.out.println(user.getUsername());
//         System.out.println(user.getRole());
//         Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//         grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
//         return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
//     }
// }
