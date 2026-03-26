package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;

import java.util.List;

public interface UserService {

    public void saveUser(UserCreateRequest request);

    public List<UserResponse> getUsers();

    public void updateUser(UserUpdateRequest request);

    public void deleteUser(String name);
}
