# Chapter 09 - REST API Layer (Controller Deep Dive)

> **Goal:** Understand Spring Boot Controllers, REST APIs, HTTP Methods, Request Mapping, Request Body, Path Variables, Validation, and API Flow.

---

# Index

- What is REST API?
- What is Controller Layer?
- @RestController
- HTTP Methods
- @GetMapping
- @PostMapping
- @PutMapping
- @DeleteMapping
- Request Flow
- Dependency Injection in Controller
- @RequestBody
- @PathVariable
- @RequestParam
- @Valid
- JSON Request/Response
- Postman Testing
- HTTP Status Codes
- Common Mistakes
- Interview Questions
- Summary

---

# What is REST API?

REST stands for:

```
Representational State Transfer
```

REST API allows communication between:

```
Client

↓

Server
```

Examples:

Client:

- Browser
- Mobile App
- React Application
- Postman

Server:

- Spring Boot Application

---

# Real Example

Suppose React frontend wants student data.

Request:

```
GET /student/10
```

Spring Boot:

```
Find Student with ID 10
```

Response:

```json
{
    "id":10,
    "firstName":"Harsh",
    "lastName":"Jain"
}
```

---

# Controller Layer

Controller handles:

- Receiving HTTP requests
- Calling Service methods
- Returning responses

Flow:

```
Client

↓

Controller

↓

Service

↓

Repository

↓

Database
```

---

# Why Controller?

Controller separates:

```
HTTP Handling

from

Business Logic
```

Controller should not contain database code.

---

# @RestController

Example:

```java
@RestController
public class StudentController {

}
```

It tells Spring:

"This class contains REST API endpoints."

---

# What Does @RestController Do?

It combines:

```
@Controller

+

@ResponseBody
```

---

# @Controller vs @RestController

## @Controller

Used for MVC applications.

Returns:

```
HTML Page
```

Example:

```
home.html
```

---

## @RestController

Used for REST APIs.

Returns:

```
JSON Data
```

Example:

```json
{
"name":"Harsh"
}
```

---

# Dependency Injection in Controller

Your code:

```java
private final StudentService studentService;


@Autowired
public StudentController(
StudentService studentService){

    this.studentService =
    studentService;

}
```

---

# Internal Working

Application starts:

```
Spring Container

↓

Creates StudentService Bean

↓

Creates StudentController Bean

↓

Injects StudentService

↓

Controller Ready
```

---

# HTTP Methods

REST uses HTTP methods to perform operations.

| Method | Purpose |
|-|-|
| GET | Fetch Data |
| POST | Create Data |
| PUT | Update Complete Data |
| PATCH | Update Partial Data |
| DELETE | Delete Data |

---

# GET Request

Used to retrieve data.

Example:

```
GET /student/5
```

Meaning:

"Give me student whose ID is 5."

---

# @GetMapping

Example:

```java
@GetMapping("/student/{sID}")
public Student getStudentById(){

}
```

Maps GET request to a Java method.

---

# POST Request

Used to create new data.

Example:

```
POST /student/add
```

Request Body:

```json
{
    "firstName":"Harsh",
    "lastName":"Jain"
}
```

---

# @PostMapping

Example:

```java
@PostMapping("/student/add")
public Student addStudent(){

}
```

---

# PUT Request

Used for updating existing data.

Example:

```
PUT /student/10
```

Request:

```json
{
"id":10,
"firstName":"Rahul",
"lastName":"Sharma"
}
```

---

# @PutMapping

Example:

```java
@PutMapping("/student/{studentId}")
public Student updateStudent(){

}
```

---

# DELETE Request

Used to delete data.

Example:

```
DELETE /student/10
```

---

# @DeleteMapping

Example:

```java
@DeleteMapping("/student/{studentId}")
public void deleteStudentById(){

}
```

---

# @RequestBody

Example:

```java
public Student addStudent(
@RequestBody Student student)
```

Purpose:

Converts JSON request into Java Object.

---

# Without @RequestBody

Incoming JSON:

```json
{
"firstName":"Harsh"
}
```

Spring does not know where to put it.

---

# With @RequestBody

JSON

↓

Jackson Library

↓

Java Object

↓

Student student

---

# Example

JSON:

```json
{
"firstName":"Harsh",
"lastName":"Jain"
}
```

Converted into:

```java
Student student = new Student();

student.setFirstName("Harsh");

student.setLastName("Jain");
```

---

# @PathVariable

Used to read values from URL.

Example:

URL:

```
/student/10
```

Code:

```java
@GetMapping("/student/{sID}")
public Student getStudentById(
@PathVariable long studentId)
```

---

# Internal Working

URL:

```
/student/10
```

Spring extracts:

```
10
```

and passes:

```java
studentId = 10
```

---

# Path Variable Name Matching

Example:

```java
@GetMapping("/student/{sID}")
```

Variable:

```java
@PathVariable(name="sID")
long studentId
```

Because names are different.

---

# Shortcut

If same name:

```java
@GetMapping("/student/{studentId}")
public Student getStudentById(
@PathVariable long studentId)
```

No need to write:

```java
name=""
```

---

# @RequestParam

Used for query parameters.

Example URL:

```
/student?name=Harsh
```

Code:

```java
@GetMapping("/student")
public Student search(
@RequestParam String name){

}
```

---

# Difference

## PathVariable

Part of URL.

```
/student/10
```

---

## RequestParam

After ?

```
/student?id=10
```

---

# @Valid

Example:

```java
@RequestBody
@Valid
Student student
```

Used for validation.

---

# Without @Valid

```java
{
"firstName":""
}
```

Accepted.

---

# With @Valid

Checks:

```java
@NotBlank
private String firstName;
```

Empty value rejected.

---

# JSON Request and Response

Spring Boot automatically converts:

Java Object

↓

JSON

using:

```
Jackson Library
```

---

# Example

Java:

```java
Student student;
```

Response:

```json
{
"id":1,
"firstName":"Harsh",
"lastName":"Jain"
}
```

---

# Complete API Flow

Example:

```
POST /student/add
```

Request:

```json
{
"firstName":"Harsh",
"lastName":"Jain"
}
```

Flow:

```
Postman

↓

DispatcherServlet

↓

StudentController

↓

@RequestBody converts JSON

↓

Student Object

↓

StudentService

↓

StudentRepository

↓

Hibernate

↓

Database

↓

Student Object

↓

JSON Response

↓

Postman
```

---

# DispatcherServlet

It is the Front Controller of Spring MVC.

All requests first come here.

```
Client

↓

DispatcherServlet

↓

Controller
```

It decides which controller method should execute.

---

# HTTP Status Codes

## 200 OK

Request successful.

---

## 201 Created

New resource created.

---

## 400 Bad Request

Invalid input.

---

## 404 Not Found

Resource not found.

---

## 500 Internal Server Error

Server problem.

---

# Postman Testing

## Add Student

Method:

```
POST
```

URL:

```
localhost:8080/student/add
```

Body:

JSON

```json
{
"firstName":"Harsh",
"lastName":"Jain"
}
```

---

## Get Student

Method:

```
GET
```

URL:

```
localhost:8080/student/1
```

---

## Delete Student

Method:

```
DELETE
```

URL:

```
localhost:8080/student/1
```

---

# Common Mistakes

❌ Writing database logic inside Controller.

---

❌ Returning Entity directly in complex projects.

Usually DTOs are preferred.

---

❌ Forgetting @RequestBody.

JSON will not convert.

---

❌ Wrong PathVariable name.

Example:

```java
/{id}
```

but

```java
@PathVariable studentId
```

---

❌ Not using validation.

Invalid data can enter database.

---

# Interview Questions

### What is REST API?

An API style that uses HTTP methods to communicate between client and server.

---

### Difference between @Controller and @RestController?

@Controller returns views.

@RestController returns data like JSON.

---

### What does @RequestBody do?

Converts incoming JSON into Java Object.

---

### What does @PathVariable do?

Extracts values from URL path.

---

### What does @RequestParam do?

Extracts query parameters.

---

### Difference between PUT and PATCH?

PUT updates complete resource.

PATCH updates partial resource.

---

### What is DispatcherServlet?

Front controller that receives every request and routes it to the correct controller.

---

# Summary

In this chapter we learned:

- REST API basics.
- Controller responsibility.
- @RestController.
- HTTP methods.
- Mapping annotations.
- RequestBody.
- PathVariable.
- RequestParam.
- Validation.
- JSON conversion.
- Postman testing.
- HTTP status codes.
- Complete request flow.

---

# Quick Revision

```
Client

↓

HTTP Request

↓

DispatcherServlet

↓

Controller

↓

Service

↓

Repository

↓

Database

↓

JSON Response
```

---

# Progress

- [x] REST API
- [x] Controller Layer
- [x] @RestController
- [x] HTTP Methods
- [x] Mapping Annotations
- [x] RequestBody
- [x] PathVariable
- [x] RequestParam
- [x] Validation
- [x] JSON Conversion
- [x] Postman Testing
- [x] Interview Questions