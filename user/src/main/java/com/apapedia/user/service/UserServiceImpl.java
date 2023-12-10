package com.apapedia.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.apapedia.user.exception.InsufficientBalanceException;
import com.apapedia.user.exception.UserNotFoundException;
import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.payload.user.UpdateUserRequestDTO;
import com.apapedia.user.repository.CustomerDb;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    SellerDb sellerDb;

    @Autowired
    CustomerDb customerDb;

    @Qualifier("sellerServiceImpl")

    @Autowired
    SellerService sellerService;

    @Qualifier("customerServiceImpl")

    @Autowired
    CustomerService customerService;

    @Override
    public boolean existsByUsername(String username) {
        return sellerDb.existsByUsername(username) | customerDb.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return sellerDb.existsByEmail(email) | customerDb.existsByEmail(email);
    }

    @Override
    public boolean checkAccountExists(String username) {
        UserModel user = getUserByUsername(username);
        if (user == null) {
            return false;
        }
        return user.isDeleted();
    }

    @Override 
    public UserModel deleteUser(UserModel user) {
        if (user instanceof Seller) {
            Seller seller = (Seller) user;
            sellerService.deleteSeller(seller);
            return seller;
        } else {
            Customer customer = (Customer) user;
            customerService.deleteCustomer(customer);
            return customer;
        }
    }

    @Override
    public UserModel getUserById(UUID id) {
        Seller seller = sellerService.getSellerById(id);
        Customer customer = customerService.getCustomerById(id);

        if (seller == null && customer == null) {
            return null;
        } else if (seller != null && customer == null) {
            return seller;
        } else if (customer != null && seller == null) {
            return customer;
        } 
        return null;
    }

    @Override
    public UserModel getUserByUsername(String username) {
        Seller seller = sellerService.getSellerByUsername(username);
        Customer customer = customerService.getCustomerByUsername(username);

        if (seller == null && customer == null) {
            return null;
        } else if (seller != null && customer == null) {
            return seller;
        } else if (customer != null && seller == null) {
            return customer;
        } 
        return null;

    }

    @Override
    public List<UserModel> getAllUser() {
        List<UserModel> listAllUser = new ArrayList<>();
        listAllUser.addAll(sellerDb.findAll());
        listAllUser.addAll(customerDb.findAll());
        return listAllUser;
    }

    @Override
    public boolean isEmailExistsById(String email, UUID id) {
        return getAllUser().stream().anyMatch(b -> b.getEmail().equalsIgnoreCase(email) && !b.getId().equals(id));
    }

    @Override
    public boolean isUsernameExistsById(String username, UUID id) {
        return getAllUser().stream().anyMatch(b -> b.getUsername().equals(username) && !b.getId().equals(id));
    }

    @Override
    public UserModel update(UpdateUserRequestDTO updateUserRequestDTO) {
        UserModel user = getUserById(updateUserRequestDTO.getId());
        if (user != null) {
            user.setAddress(updateUserRequestDTO.getAddress());
            user.setUpdatedAt(LocalDateTime.now());
            user.setEmail(updateUserRequestDTO.getEmail());
            user.setNama(updateUserRequestDTO.getNama());
            user.setUsername(updateUserRequestDTO.getUsername());
            user.setDeleted(false);

            if (user instanceof Seller) {
                sellerDb.save((Seller) user);
            } else {
                user.setPassword(updateUserRequestDTO.getPassword());
                customerDb.save((Customer) user);
            }
        }
        return user;
    }

    @Override
    public void updateBalance(UUID id, long newBalance) {
        UserModel user = getUserById(id);

        if (user == null) {
            throw new UserNotFoundException("Can't find user with that id");
        }

        if (user instanceof Seller) {
            if (newBalance > user.getBalance()) {
                throw new InsufficientBalanceException("Balance is not enough to withdraw");
            }

            user.setBalance(user.getBalance() - newBalance);

            sellerDb.save((Seller) user);

        } else {
            user.setBalance(user.getBalance() + newBalance);

            customerDb.save((Customer) user);
        }
    }

}
