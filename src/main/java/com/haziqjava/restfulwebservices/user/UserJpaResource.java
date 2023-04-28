package com.haziqjava.restfulwebservices.user;

import com.haziqjava.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {
  private UserRepository repository;
  private UserDaoService service;

  public UserJpaResource(UserDaoService service, UserRepository repository) {
    this.repository = repository;
    this.service = service;
  }
  @GetMapping(path = "/jpa/users")
  public List<User> retrieveAllUsers() {
    return repository.findAll();
  }

//  INFO: A simple EntityModel wrapping a domain object and adding links to it. (Used for Haeteos)
  @GetMapping(path = "/jpa/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable int id) {
    Optional<User> user = repository.findById(id);
    if (user.isEmpty())
      throw new UserNotFoundException("id: " + id);
    EntityModel<User> entityModel = EntityModel.of(user.get());
//    INFO: Builder to ease building Link instances pointing to Spring MVC controllers.

    WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    entityModel.add(link.withRel("all-users"));
    return entityModel;
  }

  @DeleteMapping(path = "/jpa/users/{id}")
  public void deleteUser(@PathVariable int id) {
    repository.deleteById(id);
  }


//  The code first calls the fromCurrentRequest() method on the ServletUriComponentsBuilder
//  class to get a builder instance that is initialized with the current request's URI as the base.
//  It then calls the path("/{id}") method to append a path segment to the URI template, where {id}
//  is a placeholder for the unique identifier of the newly created or saved resource.
//
//  The buildAndExpand(savedUser.getId()) method is then called to substitute the {id}
//  placeholder in the URI template with the actual identifier value of the saved user.
//  This results in a URI that uniquely identifies the saved user resource.
//
//  Finally, the toUri() method is called to convert the URI to a java.net.URI object that can
//  be returned to the client in a response message or used in further processing.
  @PostMapping(path = "/jpa/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    User savedUser = repository.save(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
