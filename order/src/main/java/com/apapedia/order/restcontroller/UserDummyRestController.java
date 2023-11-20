package com.apapedia.order.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.order.dto.UserDummyMapper;
import com.apapedia.order.dto.request.CreateUserDummyRequestDTO;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.restservice.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserDummyRestController {
    @Autowired
    private UserDummyMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRestController cartController;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CreateUserDummyRequestDTO userDTO){
        try{
            UserDummy userDummy = userMapper.createUserDummyRequestDTOToUserDummy(userDTO);
            userService.createUserDummy(userDummy);

            ResponseEntity<Cart> response = cartController.addCart(userDummy.getUserId());
            if (response.getStatusCode() != HttpStatus.NOT_FOUND) {
                userDummy.setCart(response.getBody());
                return ResponseEntity.ok("User created");
            } else {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
                );
            }
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User not found after creation. Request body has invalid type or missing field", e
            );
        }
        
    }
}
