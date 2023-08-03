package com.br.springproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatters:defaut}")
    private String corsOriginPatterss = "";

    @Override
    public void addCorsMappings(CorsRegistry registry){
        String[] allowedOrigins = corsOriginPatterss.split(",");
        registry.addMapping("/**")
                //.allowedMethods("GET", "POST", "PUT", "DELETE");
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true);
    }

}
