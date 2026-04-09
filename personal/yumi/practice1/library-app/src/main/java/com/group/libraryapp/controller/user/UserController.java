package com.group.libraryapp.controller.user;


import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = new UserService(jdbcTemplate);
    }

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
    }


    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);

    }


}