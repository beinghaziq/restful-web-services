package com.haziqjava.restfulwebservices.jpa;

import com.haziqjava.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
