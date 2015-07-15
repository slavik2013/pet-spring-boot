package ua.in.petybay.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by slavik on 14.07.15.
 */
@Component
public class PasswordEncoderService {

    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
