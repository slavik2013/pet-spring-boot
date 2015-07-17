package ua.in.petybay.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import ua.in.petybay.security.*;

/**
 * Created by slavik on 31.03.15.
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecUserDetailsService userDetailsService;

    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private RESTAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home", "/registration","/register", "/regitrationConfirm","/main","/index","/pet","/petlist","/makelogin").permitAll()
//                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.csrf().disable();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.formLogin().successHandler(authenticationSuccessHandler);
        http.formLogin().failureHandler(authenticationFailureHandler);
    }


    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderService.getPasswordEncoder());

    }


}
