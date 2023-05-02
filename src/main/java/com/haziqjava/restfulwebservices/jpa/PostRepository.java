package com.haziqjava.restfulwebservices.jpa;

import com.haziqjava.restfulwebservices.user.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostModel, Integer> {
}
