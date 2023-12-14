package com.apapedia.frontend.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.apapedia.frontend.payloads.OrderDTO;
import com.apapedia.frontend.payloads.UpdateBalanceUser;
import com.apapedia.frontend.payloads.UserDTO;
import com.apapedia.frontend.restService.OrderRestService;
import com.apapedia.frontend.restService.UserRestService;
import com.apapedia.frontend.service.FrontEndService;
import com.apapedia.frontend.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    Logger logger = LoggerFactory.getLogger(FrontEndController.class);

    @Autowired
    OrderRestService orderRestService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRestService userRestService;

    @GetMapping("/history")
    public String showOrderHistory(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {
            UUID idSeller = jwtService.getIdFromJwtToken(jwtToken);
            List<OrderDTO> orders = orderRestService.getOrderHistory(idSeller, jwtToken);

            model.addAttribute("listOrder", orders);
            return "history";
        }
    }

    @GetMapping("/{id}/{status}/update")
    private String updateOrderStatus(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, 
                                    HttpServletRequest request, @PathVariable("id") UUID id, @PathVariable("status") int status) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {
            UUID idSeller = jwtService.getIdFromJwtToken(jwtToken);

            if (status != 3) {
                orderRestService.updateStatus(id, status, jwtToken);

                if (status == 4) {
                    List<OrderDTO> orders = orderRestService.getOrderBySeller(idSeller, jwtToken);

                    int total = 0;
                    for (OrderDTO listOrder : orders) {
                        if (listOrder.getId().equals(id)) {
                            total = listOrder.getTotalPrice();
                        }
                    }

                    var updateRequest = new UpdateBalanceUser();
                    updateRequest.setId(idSeller);
                    updateRequest.setBalance(total);
                    userRestService.updateBalanceV2(updateRequest, jwtToken);
                }

                return "redirect:/history";
            } else {
                return "redirect:/history";
            }
        }
    }
}
