package ua.in.petybay.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ua.in.petybay.Events.OnRegistrationCompleteEvent;
import ua.in.petybay.dao.AdvertVerificationTokenRepository;
import ua.in.petybay.dao.UserRepository;
import ua.in.petybay.dao.VerificationTokenRepository;
import ua.in.petybay.entity.Advert;
import ua.in.petybay.entity.AdvertVerificationToken;
import ua.in.petybay.entity.User;
import ua.in.petybay.entity.VerificationToken;
import ua.in.petybay.security.PasswordEncoderService;
import ua.in.petybay.security.SecUserDetails;
import ua.in.petybay.security.SecUserDetailsService;
import ua.in.petybay.service.IAdvertService;
import ua.in.petybay.service.IUserService;

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

    @Autowired
    private IUserService service;

    @Autowired
    private IAdvertService advertService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private AdvertVerificationTokenRepository advertVerificationTokenRepository;

    @Autowired
    SecUserDetailsService secUserDetailsService;

//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        binder.setDisallowedFields(new String[]{"passwordconfirm"});
//    }

//    @Autowired(required = false)
//    @Qualifier("authenticationManager")
//    AuthenticationManager authenticationManager;
//
//    @RequestMapping(value = "/makelogin2", method = RequestMethod.POST)
//    public void loginViaAuthenticationManager(@RequestParam("username") String username,
//                                              @RequestParam("password") String password){
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication auth = authenticationManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//    }

    @RequestMapping(value = "/makelogin", method = RequestMethod.POST)
    public void login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @Qualifier("passwordEncoderService") PasswordEncoderService passwordEncoderService){

        PasswordEncoder passwordEncoder = passwordEncoderService.getPasswordEncoder();
        UserDetails userDetails = secUserDetailsService.loadUserByUsername(username);

        if(password != null && passwordEncoder.matches(password, userDetails.getPassword())) {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
            System.out.println("login() ok");
        }

        System.out.println("login() username = " + username + " ; password = " + password);
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/user")
    public User getUserInfo(@AuthenticationPrincipal SecUserDetails secUserDetails){
//        SecUserDetails user = (SecUserDetails)(((Authentication) principal).getPrincipal());
        User user = secUserDetails.getUser();
        System.out.println("my rest user = " + user);
        return  user;
    }


    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/admin")
    public String getAdminInfo(){
        return "ok, getAdminInfo: \"ROLE_ADMIN\"";
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/plain")
    public String register(@RequestBody User user, @Qualifier("passwordEncoderService") PasswordEncoderService passwordEncoderService,
                           WebRequest request, Errors errors){
        for(ObjectError objectError : errors.getAllErrors()){
            System.out.println("objectError = " + objectError);
        }

        System.out.println("register() user = " + user);

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

    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token) {

        VerificationToken verificationToken = service.getVerificationToken(token);

        if (verificationToken == null) {
            System.out.println("verificationToken is null");
            return "verificationToken is null";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
//        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//            System.out.println("account expired");
//            return "account expired";
//        }

        user.setEnabled(true);
        service.saveRegisteredUser(user);

        verificationToken.setVerified(true);
        verificationTokenRepository.save(verificationToken);
        return "confirmed";
    }

    @RequestMapping(value = "/activateAdvertisement", method = RequestMethod.GET)
    public String activateAdvertisement(@RequestParam("token") String token) {

        AdvertVerificationToken verificationToken = advertService.getVerificationToken(token);

        if (verificationToken == null) {
            System.out.println("verificationToken is null");
            return "verificationToken is null";
        }

        Advert advert = verificationToken.getAdvert();

        if (Advert.STATE.WAITING.equals(advert.getState())) {
            advert.setState(Advert.STATE.ACTIVE);

            advertService.save(advert);
        }
        verificationToken.setVerified(true);
        advertVerificationTokenRepository.save(verificationToken);
        return "advertisement activated";
    }


    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/confirmAd/{adId}")
    public String confirmUserAd(@PathVariable("adId") String adId,
            @AuthenticationPrincipal SecUserDetails secUserDetails){

        User user = secUserDetails.getUser();

        Advert advert = advertService.findOne(adId);
        User advertUser = advert.getUser();

        if (advertUser.getEmail().equals(user.getEmail())) {
            advert.setState(Advert.STATE.ACTIVE);
            advertService.save(advert);
        }
      return "ad confirmed";
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/deactivateAd/{adId}")
    public String deactivateUserAd(@PathVariable("adId") String adId,
                                @AuthenticationPrincipal SecUserDetails secUserDetails){

        User user = secUserDetails.getUser();

        Advert advert = advertService.findOne(adId);
        User advertUser = advert.getUser();

        if (advertUser.getEmail().equals(user.getEmail())) {
            advert.setState(Advert.STATE.NONACTIVE);
            advertService.save(advert);
        }
        return "ad deactivated";
    }


    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        System.out.println("getAllUsers()");
        List<User> users = new ArrayList<User>();
        for(User user : userRepository.findAll()){
             users.add(user);
         }
        return users;
    }

}

