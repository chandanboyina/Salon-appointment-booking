//package com.chandan.config;
package com.chandan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.FormHttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        // Add the converter for form-urlencoded data
//        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
//        return restTemplate;
//    }
//}