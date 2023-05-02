package com.haziqjava.restfulwebservices.user;

import com.haziqjava.restfulwebservices.jpa.PostRepository;
import com.haziqjava.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostsController {
  private UserRepository userRepository;
  private PostRepository postRepository;

  public PostsController(UserRepository repository, PostRepository postRepository) {
    this.postRepository = postRepository;
    this.userRepository = repository;
  }

  @GetMapping(path = "/jpa/users/{id}/posts")
  public List<PostModel> retrievePostsForUser(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty())
      throw new UserNotFoundException("id: " + id);

    return user.get().getPosts();
  }

  @GetMapping(path = "/jpa/users/{userId}/posts/{id}")
  public PostModel retrievePostForUser(@PathVariable int userId, @PathVariable int id) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty())
      throw new UserNotFoundException("id: " + userId);

    Optional<PostModel> post = postRepository.findById(id);
    if (post.isEmpty())
      throw new UserNotFoundException("id: " + userId);

    return post.get();
  }

  @PostMapping(path = "/jpa/users/{id}/posts")
  public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @Valid @RequestBody PostModel post) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty())
      throw new UserNotFoundException("id: " + id);

    post.setUser(user.get());
    PostModel savedPost = postRepository.save(post);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedPost.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
