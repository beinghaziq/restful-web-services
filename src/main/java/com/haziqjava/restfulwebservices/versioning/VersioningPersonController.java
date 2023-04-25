package com.haziqjava.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

  @GetMapping("/v1/person")
  public PersonV1 getFirstVersionOfPerson() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping("/v2/person")
  public PersonV2 getSecondVersionOfPerson() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }

  @GetMapping(path = "/person", params = "version=1")
  public PersonV1 getFirstVersionOfPersonWithParams() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(path = "/person", params = "version=2")
  public PersonV2 getSecondVersionOfPersonWithParams() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }

  @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
  public PersonV1 getFirstVersionOfPersonWithHeaders() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
  public PersonV2 getSecondVersionOfPersonWithHeaders() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }

//  INFO: application/vnd.company.app-v1+json needs to be sent as Accept header
  @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
  public PersonV1 getFirstVersionOfPersonWithAcceptHeaders() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
  public PersonV2 getSecondVersionOfPersonWithAcceptHeaders() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }
}
