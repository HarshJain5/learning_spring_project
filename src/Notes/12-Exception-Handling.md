# Chapter 11 - Exception Handling in Spring Boot

> **Goal:** Learn how to handle errors properly in Spring Boot applications using Custom Exceptions, @ExceptionHandler, @ControllerAdvice, and Global Exception Handling.

---

# Index

- What is Exception?
- Why Exception Handling?
- Default Spring Error Response
- Custom Exception
- RuntimeException
- Throwing Exception
- @ExceptionHandler
- Global Exception Handling
- @ControllerAdvice
- @RestControllerAdvice
- Error Response Class
- Validation Exception Handling
- Complete Error Flow
- Common Mistakes
- Interview Questions
- Summary

---

# What is an Exception?

An exception is an unexpected event that interrupts the normal flow of a program.

Example:

```java
int a = 10/0;
```

Output:

```
ArithmeticException
```

---

# Real Application Example

Student API:

Request:

```
GET /student/50
```

Database:

```
No student with id 50
```

Problem:

Application should not crash.

Instead:

Return meaningful error.

---

# Why Exception Handling?

Without exception handling:

```
Error Occurs

↓

Application Stops

↓

500 Error
```

With exception handling:

```
Error Occurs

↓

Catch Exception

↓

Create Proper Response

↓

Send to Client
```

---

# Default Spring Error Response

Suppose:

```java
throw new RuntimeException(
"Student Not Found"
);
```

Spring returns:

```json
{
"timeStamp":"...",
"status":500,
"error":"Internal Server Error",
"path":"/student/10"
}
```

Problem:

- Too much information
- Not user friendly
- Wrong status code

---

# Custom Exception

Your class:

```java
public class NotFoundExceptions 
extends RuntimeException{


    public NotFoundExceptions(String message){

        super(message);

    }

}
```

---

# Why Extend RuntimeException?

Java has two types of exceptions:

```
Checked Exception

and

Unchecked Exception
```

---

# Checked Exception

Compiler forces handling.

Example:

```java
try{

}
catch(Exception e){

}
```

Example:

```java
IOException
```

---

# Unchecked Exception

Compiler does not force handling.

Example:

```
RuntimeException
NullPointerException
ArithmeticException
```

---

# Why RuntimeException for Custom Exceptions?

Because application exceptions like:

```
User Not Found

Student Not Found

Product Not Found
```

are runtime problems.

---

# Throwing Custom Exception

Your Service:

```java
public Student getStudentById(long studentId){

    Optional<Student> student =
    studentRepository.findById(studentId);


    return student.orElseThrow(
    () -> new NotFoundExceptions(
    "Student Not Found"
    ));

}
```

---

# Flow

```
Controller

↓

Service

↓

Repository

↓

No Data Found

↓

NotFoundExceptions

↓

Exception Handler

↓

Response
```

---

# @ExceptionHandler

Used to handle a specific exception.

Example:

```java
@ExceptionHandler(
NotFoundExceptions.class
)
public ResponseEntity<String>
handleException(
NotFoundExceptions exception){

    return ResponseEntity
    .status(404)
    .body(exception.getMessage());

}
```

---

# Problem with @ExceptionHandler

If we write it inside every controller:

```
StudentController

+

EmployeeController

+

ProductController
```

then duplicate code happens.

Solution:

Global Exception Handler.

---

# Global Exception Handling

Instead of writing exception code everywhere:

Create one class.

Example:

```
exceptions

    |
    |
    GlobalExceptionHandler
```

All controllers share it.

---

# @ControllerAdvice

Example:

```java
@ControllerAdvice
public class GlobalExceptionHandler {


}
```

---

# Purpose

It applies exception handling logic to all controllers.

Flow:

```
Any Controller

↓

Exception

↓

GlobalExceptionHandler

↓

Response
```

---

# @RestControllerAdvice

Most commonly used in REST APIs.

Example:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {


}
```

It combines:

```
@ControllerAdvice

+

@ResponseBody
```

Meaning:

It directly returns JSON responses.

---

# Creating Error Response Class

Instead of returning only message:

```java
"Student Not Found"
```

Create proper structure.

---

## ErrorResponse.java

```java
@Getter
@Setter
public class ErrorResponse {


    private String message;

    private int status;


    public ErrorResponse(
    String message,
    int status){

        this.message = message;
        this.status = status;

    }

}
```

---

# GlobalExceptionHandler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {


@ExceptionHandler(NotFoundExceptions.class)
public ResponseEntity<ErrorResponse>
handleNotFound(
NotFoundExceptions exception){


    ErrorResponse error =
    new ErrorResponse(
    exception.getMessage(),
    404
    );


    return new ResponseEntity<>(
    error,
    HttpStatus.NOT_FOUND
    );


}

}
```

---

# Now Response

Request:

```
GET /student/100
```

Response:

```json
{
    "message":"Student Not Found",
    "status":404
}
```

Status:

```
404 NOT FOUND
```

---

# Validation Exception Handling

Suppose Entity:

```java
@NotBlank
private String firstName;
```

Request:

```json
{
"firstName":"",
"lastName":"Jain"
}
```

Validation fails.

Spring throws:

```
MethodArgumentNotValidException
```

---

# Handling Validation Errors

Example:

```java
@ExceptionHandler(
MethodArgumentNotValidException.class
)
public ResponseEntity<?> 
handleValidation(
MethodArgumentNotValidException ex){

}
```

---

# Complete Exception Flow

```
Client

↓

Controller

↓

Service

↓

Exception Occurs

↓

Throw Custom Exception

↓

GlobalExceptionHandler

↓

Error JSON

↓

Client
```

---

# Complete Exception Package

Recommended structure:

```
exceptions

│

├── NotFoundExceptions.java

│

├── ErrorResponse.java

│

└── GlobalExceptionHandler.java
```

---

# Common Mistakes

## 1. Handling Exception in Every Controller

Bad:

```java
try{

}
catch(){

}
```

everywhere.

---

## 2. Returning Only String Error

Bad:

```java
return "Error";
```

Better:

```json
{
"message":"Error",
"status":404
}
```

---

## 3. Using Wrong Status Code

Not Found:

```
404
```

Not:

```
500
```

---

## 4. Exposing Database Errors

Never send:

```
SQL Exception Details
```

to users.

---

# Interview Questions

### What is Exception Handling?

A mechanism to handle runtime errors and provide meaningful responses.

---

### Why use custom exceptions?

To represent application-specific errors.

Example:

```
StudentNotFoundException
```

---

### Difference between @ControllerAdvice and @RestControllerAdvice?

@ControllerAdvice → For MVC applications.

@RestControllerAdvice → For REST APIs returning JSON.

---

### What is @ExceptionHandler?

Annotation used to handle specific exceptions.

---

### Why use Global Exception Handler?

To avoid duplicate exception handling code across controllers.

---

### What happens when RuntimeException is thrown?

Spring searches for matching exception handlers and returns the configured response.

---

# Summary

In this chapter we learned:

- Exception basics.
- Why exception handling is required.
- Custom exceptions.
- RuntimeException.
- @ExceptionHandler.
- @ControllerAdvice.
- @RestControllerAdvice.
- Error Response.
- Validation error handling.
- Complete error flow.

---

# Quick Revision

```
Exception Occurs

        ↓

Custom Exception

        ↓

Global Exception Handler

        ↓

ErrorResponse Object

        ↓

JSON Response

        ↓

Client
```

---

# Progress

- [x] Custom Exception
- [x] RuntimeException
- [x] @ExceptionHandler
- [x] @ControllerAdvice
- [x] @RestControllerAdvice
- [x] Error Response
- [x] Validation Handling
- [x] Global Exception Flow
- [x] Interview Questions
