package com.haziqjava.restfulwebservices.user;

import com.haziqjava.restfulwebservices.jpa.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PostsController {
  private UserRepository userRepository;

  public PostsController(UserRepository repository) {
    this.userRepository = repository;
  }

  @GetMapping(path = "/jpa/users/{id}/posts")
  public List<PostModel> retrievePostsForUser(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty())
      throw new UserNotFoundException("id: " + id);

    return user.get().getPosts();
  }
}
