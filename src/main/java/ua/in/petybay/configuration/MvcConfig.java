package ua.in.petybay.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by slavik on 31.03.15.
 */

//public class MvcConfig{}
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/home").setViewName("home");
////        registry.addViewController("/").setViewName("home");
////        registry.addViewController("/hello").setViewName("hello");
////        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/multi").setViewName("multi.html");
//        registry.addViewController("/upload").setViewName("upload.html");
        registry.addViewController("/index").setViewName("index.html");
//
//        registry.addViewController("/error").setViewName("error");
    }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}