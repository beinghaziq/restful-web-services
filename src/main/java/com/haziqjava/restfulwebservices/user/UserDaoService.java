package com.haziqjava.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
  private static List<User> users = new ArrayList<>();
  private static int userCount = 0;

  static {
    users.add(new User(++ userCount, "Adam", LocalDate.now().minusYears(30)));
    users.add(new User(++ userCount, "abc", LocalDate.now().minusYears(23)));
    users.add(new User(++ userCount, "new a", LocalDate.now().minusYears(3)));
  }

  public List<User> findAll() {
    return users;
  }

//  .get() will throw an exception if record not found. That's why we are using orElse
  public User findOne(int id) {
    Predicate<? super User> predicate = user -> user.getId().equals(id);
    return users.stream().filter(predicate).findFirst().orElse(null);
  }

  public User save(User user) {
    user.setId(++ userCount);
    users.add(user);
    return user;
  }
}

//Predicate: (Kind of a lambda function in ROR)
//        In Java, the Predicate interface is a functional interface that represents a
//        predicate or a boolean-valued function that takes one argument and returns a
//        boolean value. The Predicate interface is a part of the java.util.function package
//        and is typically used to filter collections or to test conditions in various operations.
//
//        List<String> list = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
//
//        Predicate<String> startsWithA = s -> s.startsWith("a");
//        List<String> filteredList = list.stream().filter(startsWithA).collect(Collectors.toList());



//stream():
//
//        In Java, stream() is a method that is available on collections and other data structures,
//        which allows you to perform various operations on the elements of the collection.
//        A stream is a sequence of elements that can be processed in parallel or sequentially.
//
//        When you call stream() on a collection, it returns a stream of elements from the collection.
//        You can then use various methods available on the stream to process the elements in a flexible
//        and concise way.
//
//        Some of the common operations that you can perform on a stream include filtering, sorting,
//        mapping, reducing, and collecting the elements.