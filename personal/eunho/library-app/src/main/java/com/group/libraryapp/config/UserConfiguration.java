package com.group.libraryapp.config;


import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class UserConfiguration {

//    @Bean
//    public UserRepository userRepository(JdbcTemplate jdbcTemplate){
//        return new UserRepository(jdbcTemplate);
//    }
//
//    @Bean
//    public UserService userService(UserRepository userRepository){
//        return new UserService(userRepository);
//    }
}
