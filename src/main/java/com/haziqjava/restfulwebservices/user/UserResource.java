package com.haziqjava.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
  private UserDaoService service;

  public UserResource(UserDaoService service) {
    this.service = service;
  }
  @GetMapping(path = "/users")
  public List<User> retrieveAllUsers() {
    return service.findAll();
  }

//  INFO: A simple EntityModel wrapping a domain object and adding links to it. (Used for Haeteos)
  @GetMapping(path = "/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable int id) {
    User user = service.findOne(id);
    if (user == null)
      throw new UserNotFoundException("id: " + id);
    EntityModel<User> entityModel = EntityModel.of(user);
//    INFO: Builder to ease building Link instances pointing to Spring MVC controllers.

    WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    entityModel.add(link.withRel("all-users"));
    return entityModel;
  }

  @DeleteMapping(path = "/users/{id}")
  public void deleteUser(@PathVariable int id) {
    service.deleteById(id);
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
  @PostMapping(path = "/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    User savedUser = service.save(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
