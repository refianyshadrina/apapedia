package com.apapedia.catalog.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apapedia.catalog.dto.UserDTO;
import com.apapedia.catalog.restservice.UserRestService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Qualifier("userRestServiceImpl")
    @Autowired
    private UserRestService userRestService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            UserDTO user = userRestService.getUserByUsername(username);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } catch (RuntimeException e) {
            throw new UsernameNotFoundException("Username not found!");
        }
        
    }

}
