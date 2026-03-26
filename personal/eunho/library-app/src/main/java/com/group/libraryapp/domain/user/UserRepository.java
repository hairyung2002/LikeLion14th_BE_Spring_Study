package com.group.libraryapp.domain.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
