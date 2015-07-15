package ua.in.petybay.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import ua.in.petybay.dao.UserRepository;
import ua.in.petybay.dao.VerificationTokenRepository;
import ua.in.petybay.entity.User;
import ua.in.petybay.entity.VerificationToken;
import ua.in.petybay.security.OnRegistrationCompleteEvent;
import ua.in.petybay.security.PasswordEncoderService;
import ua.in.petybay.security.SecUserDetails;
import ua.in.petybay.service.IUserService;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by slavik on 14.07.15.
 */
@RestController
public class MyController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    ApplicationEventPublisher eventPublisher;


    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/user")
    public String getUserInfo(@AuthenticationPrincipal SecUserDetails secUserDetails){
//        SecUserDetails user = (SecUserDetails)(((Authentication) principal).getPrincipal());
        User user = secUserDetails.getUser();
        System.out.println("my rest user = " + user);
        return "ok, getUserInfo: \"ROLE_USER\"  : " + user;
    }


    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/admin")
    public String getAdminInfo(){
        return "ok, getAdminInfo: \"ROLE_ADMIN\"";
    }


    @PermitAll
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Transactional
    public String register(User user, @Qualifier("passwordEncoderService") PasswordEncoderService passwordEncoderService,
                           WebRequest request, Errors errors){

        if (!service.emailExist(user.getEmail())) {

            PasswordEncoder passwordEncoder = passwordEncoderService.getPasswordEncoder();
            System.out.println("passwordEncoder = " + passwordEncoder);
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            List<String> authorities = new ArrayList<String>();
            authorities.add("ROLE_USER");
            user.setAuthority(authorities);

            user.setEnabled(false);

            System.out.println("user = " + user);

            userRepository.save(user);


            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));


            return "ok";
        }
        return "user with this email already exist";
    }

    @Autowired
    private IUserService service;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token) {

        VerificationToken verificationToken = service.getVerificationToken(token);

        if (verificationToken == null) {
            System.out.println("verificationToken is null");
            return "verificationToken is null";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            System.out.println("account expired");
            return "account expired";
        }

        user.setEnabled(true);
        service.saveRegisteredUser(user);

        verificationToken.setVerified(true);
        verificationTokenRepository.save(verificationToken);
        return "confirmed";
    }



}

