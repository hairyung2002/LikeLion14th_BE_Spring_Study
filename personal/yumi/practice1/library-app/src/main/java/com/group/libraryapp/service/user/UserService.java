package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(JdbcTemplate jdbcTemplate) {
        userRepository = new UserRepository(jdbcTemplate);
    }

    public void updateUser(JdbcTemplate jdbcTemplate, UserUpdateRequest request) {
        if (userRepository.isUserNotExist(jdbcTemplate, request.getId())) {
            throw new IllegalArgumentException();
        }
        userRepository.updateUserName(jdbcTemplate, request.getName(), request.getId());
    }

    public void saveUser(UserCreateRequest request) {
        userRepository.saveUser(request.getName(), request.getAge());
    }

    public void deleteUser(String name) {
        if (userRepository.isUserNotExist(name)) {
            throw new IllegalArgumentException();
        }
        userRepository.deleteUser(name);
    }

    public List<UserResponse> getUsers() {
        return userRepository.getUsers();
    }
}
