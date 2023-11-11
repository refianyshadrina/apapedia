package com.apapedia.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerDb sellerDb;

    private Authentication userAuthentication;

    @Override
    public List<Seller> getAllSeller(){
        return sellerDb.findAll();
    }

    @Override
    public Seller getSellerByUsername(String username){
        for (Seller seller : getAllSeller()) {
            if (seller.getUsername().equalsIgnoreCase(username)) {
                return seller;
            }
        }
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return sellerDb.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return sellerDb.existsByEmail(email);
    }

    @Override
    public void save(Seller user) {
        user.setPassword(encrypt(user.getPassword()));
        sellerDb.save(user);
    }

    @Override
    public String encrypt(String password) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String hashedPassword = passwordEncoder.encode(password);
      return hashedPassword;
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //             Seller user = sellerDb.findByUsername(username);
    //     if (user == null) {
    //         throw new UsernameNotFoundException("Username not found!");
    //     }

    //     Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    //     grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
    //     return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    // }

    @Override
    public Authentication getAuthentication(){
        return this.userAuthentication;
    }

    public void setAuthentication(Authentication newAuthentication){
        this.userAuthentication = newAuthentication;
    }


}
