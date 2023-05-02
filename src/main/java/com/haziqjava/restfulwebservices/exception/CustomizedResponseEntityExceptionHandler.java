package com.haziqjava.restfulwebservices.exception;

import com.haziqjava.restfulwebservices.user.PostNotFoundException;
import com.haziqjava.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
//INFO:
//@ControllerAdvice is a specialization of the @Component annotation which allows to handle exceptions
//        across the whole application in one global handling component. It can be viewed as an interceptor
//        of exceptions thrown by methods annotated with @RequestMapping and similar.
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request){
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
            ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({UserNotFoundException.class, PostNotFoundException.class})
  public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request){
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
            ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
  }

  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
            "Total: " + ex.getErrorCount() + " First Error: " + ex.getFieldError().getDefaultMessage(),
            request.getDescription(false));

//    Info: logging all the errors as a string
    ex.getFieldErrors().stream().forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
