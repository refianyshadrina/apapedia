package com.apapedia.user.config.userdetails;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.apapedia.user.repository.CustomerDb;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SellerDb sellerDb;

    @Autowired
    private CustomerDb customerDb;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var seller = sellerDb.findByUsername(username);
        var customer = customerDb.findByUsername(username);

        if (customer != null && seller == null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(customer.getRole()));
            return new User(customer.getUsername(), customer.getPassword(), grantedAuthorities);
        } else if (seller != null && customer == null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(seller.getRole()));
            return new User(seller.getUsername(), seller.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("Username not found!");
        }

        
    }

}
