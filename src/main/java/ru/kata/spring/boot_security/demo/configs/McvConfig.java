package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class McvConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/login");
        registry.addViewController("/admin").setViewName("adminPage");
        registry.addViewController("/user").setViewName("user");
    }

}