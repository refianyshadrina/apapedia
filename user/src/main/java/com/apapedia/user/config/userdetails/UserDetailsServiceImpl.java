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

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Seller;
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
        System.out.println("masuk load");
        Seller seller = sellerDb.findByUsername(username);
        Customer customer = customerDb.findByUsername(username);
        System.out.println("nyampe kesini?");
        if (seller == null && customer == null) {
            System.out.println("masuk username not found?");
            throw new UsernameNotFoundException("Username not found!");
        } else if (customer != null ) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(customer.getRole()));
            return new User(customer.getUsername(), customer.getPassword(), grantedAuthorities);
        } else {
            System.out.println("masuk ke else?");
            System.out.println("customer null? " + (customer==null));
            System.out.println("seller null? " + (seller==null));
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            System.out.println("sini pi");
            grantedAuthorities.add(new SimpleGrantedAuthority(seller.getRole()));
            System.out.println("sini piyy");
            return new User(seller.getUsername(), seller.getPassword(), grantedAuthorities);
        }

        
    }

}
