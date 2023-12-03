package com.apapedia.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import org.slf4j.Logger;
import com.apapedia.frontend.payloads.JwtResponse;
import com.apapedia.frontend.payloads.UserDTO;
import com.apapedia.frontend.payloads.UpdateBalanceUser;
import com.apapedia.frontend.payloads.UpdateUserRequest;
import com.apapedia.frontend.restService.UserRestService;
import com.apapedia.frontend.security.xml.Attributes;
import com.apapedia.frontend.security.xml.ServiceResponse;
import com.apapedia.frontend.service.FrontEndService;
import com.apapedia.frontend.service.JwtService;
import com.apapedia.frontend.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.core.Authentication;
import java.security.Principal;


@Controller
// seller controller
public class FrontEndController {

    Logger logger = LoggerFactory.getLogger(FrontEndController.class);

    private WebClient webClient = WebClient.builder()
                                    .codecs(configurer -> configurer.defaultCodecs()
                                    .jaxb2Decoder(new Jaxb2XmlDecoder()))
                                    .build();


    @Qualifier("userRestServiceImpl")
    @Autowired
    UserRestService userRestService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    JwtService jwtService;

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response) {
        frontEndService.setCookie(response, null);
        return "redirect:/";
    }

    @GetMapping("/signup")
    private String formRegister(Model model) {
        model.addAttribute("registerRequest", new UserDTO());
        return "registration";
    }

    @GetMapping("/signup-v2")
    private String formRegister2(Model model) {
        model.addAttribute("registerRequest", new UserDTO());
        model.addAttribute("error", "Username not found. Are you sure you are registered?");
        return "registration";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute UserDTO registerRequest, RedirectAttributes redirectAttrs) {

        try {
            userRestService.createUser(registerRequest);
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        }

        redirectAttrs.addFlashAttribute("success", "Please login to system");
        return "redirect:/login-sso";
    }

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
        @RequestParam(value = "ticket", required = false) String ticket,
        HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs
    ) {

        try {

            ServiceResponse serviceResponse = this.webClient.get().uri(
                    String.format(
                            Setting.SERVER_VALIDATE_TICKET,
                            ticket,
                            Setting.CLIENT_LOGIN
                    )
            ).retrieve().bodyToMono(ServiceResponse.class).block();

            Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
            String username = serviceResponse.getAuthenticationSuccess().getUser();
            String name = attributes.getNama(); 
            var token = userRestService.getTokenForSSO(username, name);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, "apapedia", null);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // set cookie
            frontEndService.setCookie(response, token);

            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            httpSession.setAttribute("token", token);

            return new ModelAndView("redirect:/");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", "Username not found. Are you sure you are registered?");
            // return new ModelAndView("redirect:/signup");
            return new ModelAndView("redirect:/logout-sso-v2");
        }
    }

    @GetMapping("/login-sso")
    public ModelAndView loginSSO() {
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping("/logout-sso")
    public ModelAndView logoutSSO(Principal principal, HttpServletResponse response) {
        frontEndService.setCookie(response, null);
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }

    @GetMapping("/logout-sso-v2")
    public ModelAndView logoutSSOFailed(Principal principal, HttpServletResponse response) {
        frontEndService.setCookie(response, null);
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGIN_FAILED);
    }

    // @GetMapping("/login")
    // public String login(Model model){
    //     // model.addAttribute("loginRequest", new LoginRequest());
    //     // return "login";
    //     return "redirect:/login-sso";
    // }

    // sebagai front end
    // @PostMapping("/login")
    // public String authenticateAndGetToken(@ModelAttribute LoginRequest authRequest, RedirectAttributes redirectAttrs, HttpServletResponse response) {
        
    //     try {
    //         JwtResponse jwt = userRestService.login(authRequest);
    //         String jwtString = jwt.getToken();

    //         // set cookie
    //         frontEndService.setCookie(response, jwtString);

    //         System.out.println(jwtString);
    //         return "redirect:/";
    //     // } catch (UsernameNotFoundException | BadCredentialsException | IOException e) {
    //     } catch (RuntimeException e) {
    //         redirectAttrs.addFlashAttribute("error", e.getMessage());
    //         return "redirect:/login";

    //     } 

    // }

    @GetMapping("/deleteseller")
    public String deleteSeller(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);

            UserDTO seller = userRestService.getUser(id, jwtToken);

            userRestService.deleteUser(id, jwtToken);

            return "redirect:/logout-sso";
        }
        
    }

    @GetMapping("/sellerprofile")
    private String profilePage(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);

            try {
                UserDTO seller = userRestService.getUser(id, jwtToken);
                model.addAttribute("seller", seller);

                return "profile";
            } catch (RuntimeException e) {
                return "redirect:/login-sso";
            }
        }
    }

    @GetMapping("/updateprofile")
    private String updateProfileForm(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            var user = userRestService.getUser(id, jwtToken);

            var updateRequest = new UpdateUserRequest();
            updateRequest.setId(id);
            updateRequest.setAddress(user.getAddress());
            updateRequest.setEmail(user.getEmail());
            updateRequest.setNama(user.getNama());
            // updateRequest.setPassword(user.getPassword());
            updateRequest.setUsername(user.getUsername());

            model.addAttribute("updateRequest", updateRequest);
            return "update-profile-form";
        }
    }

    @PostMapping("/updateprofile")
    private String update(Model model, @ModelAttribute UpdateUserRequest updateRequest, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {

            try {
                JwtResponse jwt = userRestService.update(updateRequest, jwtToken);

                frontEndService.setCookie(response, jwt.getToken());

                UserDTO seller = userRestService.getUser(jwt.getUuid(), jwt.getToken());

                redirectAttrs.addFlashAttribute("seller", seller);
                return "redirect:/sellerprofile";
            
            } catch (RuntimeException e) {
                
                redirectAttrs.addFlashAttribute("error", e.getMessage());
                return "redirect:/updateprofile";

            } 
        }
    }

    @GetMapping("/withdraw")
    private String withdrawBalance(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);

            var updateRequest = new UpdateBalanceUser();
            updateRequest.setId(id);
            updateRequest.setBalance(0);

            model.addAttribute("updateRequest", updateRequest);
            return "update-balance-form";
        }
    }

    @PostMapping("/withdraw")
    private String withdrawBalanceSuccess(Model model, @ModelAttribute UpdateBalanceUser updateRequest, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login-sso";
        } else {

            try {

                UserDTO user = userRestService.updateBalance(updateRequest, jwtToken);

                redirectAttrs.addFlashAttribute("seller", user);
                return "redirect:/sellerprofile";
            
            } catch (RuntimeException e) {
                
                redirectAttrs.addFlashAttribute("error", e.getMessage());
                return "redirect:/withdraw";

            } 
        }
    }


}

