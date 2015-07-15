package ua.in.petybay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.in.petybay.dao.UserRepository;
import ua.in.petybay.entity.User;

/**
 * Created by slavik on 14.07.15.
 */
@Component
public class SecUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        System.out.println("user = " + user);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }else{
            UserDetails details = new SecUserDetails(user);
            return details;
        }
    }
}
