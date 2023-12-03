package com.apapedia.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.exception.UserNotFoundException;
import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.user.RegisterRequestDTO;
import com.apapedia.user.repository.SellerDb;
import java.time.LocalDateTime;
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
        throw new UserNotFoundException("Username tidak ditemukan");
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

    @Override
    public Seller getSellerById(UUID id) {
        for (Seller seller : getAllSeller()) {
            if (seller.getId().equals(id)) {
                return seller;
            }
        }
        return null;
    }

    @Override
    public Seller create(RegisterRequestDTO registerRequest) {
        Seller user = new Seller();
        user.setNama(registerRequest.getNama());
        user.setUsername(registerRequest.getUsername());
        user.setPassword("apapedia");
        user.setEmail(registerRequest.getEmail());
        user.setAddress(registerRequest.getAddress());
        user.setBalance((long) 0);
        user.setCategory(registerRequest.getCategory());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole("seller");
        save(user);
        return user;
    }

    @Override
    public void deleteSeller(Seller seller) {
        sellerDb.deleteById(seller.getId());
    }

}
