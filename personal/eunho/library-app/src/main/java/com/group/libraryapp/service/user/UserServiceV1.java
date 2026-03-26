package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Qualifier("UserJdbcRepository")
public class UserServiceV1 implements UserService{


    private final UserJdbcRepository userRepository;

    public UserServiceV1(UserJdbcRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Controller가 요청이 올때 객체로 변환해주기 때문에 @RequestBody가 필요없음
    public void saveUser(UserCreateRequest request){
        userRepository.saveUser(request.getName(), request.getAge());
    }


    public List<UserResponse> getUsers() {
        return userRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {

        if(userRepository.isUserNotExist(request.getId())){
            throw new IllegalArgumentException();
        }

        userRepository.updateUserName(request.getName(), request.getId());
    }

    public void deleteUser(String name){
        if(userRepository.isUserNotExist(name)){
            throw new IllegalArgumentException();
        }

        userRepository.deleteUser(name);
    }
}
