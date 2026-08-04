# Chapter 13 - Integrating APIs with Postman

> **Goal:** Learn how to test Spring Boot REST APIs using Postman without creating a frontend.

---

# Index

- What is Postman?
- Why do we need Postman?
- Installing Postman
- Understanding APIs
- HTTP Request & Response
- Testing REST APIs
- GET Request
- POST Request
- PUT Request
- DELETE Request
- Headers
- Request Body
- Query Parameters
- Path Variables
- HTTP Status Codes
- JSON Response
- Common Errors
- Best Practices
- Interview Questions
- Summary

---

# Before Learning Postman

Suppose you have completed your Spring Boot backend.

Your application has APIs like:

```text
POST   /student/add

GET    /student/{id}

PUT    /student/{id}

DELETE /student/{id}
```

But you don't have a frontend yet.

Question:

How will you test your APIs?

Answer:

```
Postman
```

---

# What is Postman?

Postman is an API Testing Tool.

It allows developers to send HTTP requests to a server and view the response without writing a frontend.

Think of it as a client application that communicates directly with your backend.

---

# Why Do We Need Postman?

Imagine you are building a Student Management System.

Backend is ready.

Frontend is still under development.

Instead of waiting for the frontend, you can test your APIs using Postman.

```
Postman

↓

Spring Boot API

↓

Database

↓

Response

↓

Postman
```

This saves a lot of development time.

---

# Installing Postman

Download Postman from:

https://www.postman.com/downloads/

Install it like any normal application.

---

# What is an API?

API stands for

```
Application Programming Interface
```

An API allows two applications to communicate.

Example:

```
React Application

↓

REST API

↓

Spring Boot

↓

Database
```

---

# HTTP Request

Whenever Postman calls an API, it sends an HTTP Request.

A request contains:

- HTTP Method
- URL
- Headers
- Body (Optional)

Example:

```
POST

http://localhost:8080/student/add
```

---

# HTTP Response

The server processes the request and sends back a response.

A response contains:

- Status Code
- Headers
- Response Body

Example:

```json
{
    "id":1,
    "firstName":"Harsh",
    "lastName":"Jain"
}
```

---

# Understanding the URL

Example:

```
http://localhost:8080/student/add
```

Breaking it down:

```
http://

↓

Protocol

-------------------

localhost

↓

Server

-------------------

8080

↓

Port Number

-------------------

/student/add

↓

API Endpoint
```

---

# Testing POST API

Suppose we have:

```java
@PostMapping("/student/add")
public Student addStudent(
@RequestBody @Valid Student student){

    return studentService.addStudent(student);

}
```

---

## Step 1

Open Postman.

---

## Step 2

Select Method:

```
POST
```

---

## Step 3

Enter URL

```
http://localhost:8080/student/add
```

---

## Step 4

Go to

```
Body

↓

raw

↓

JSON
```

---

## Step 5

Enter JSON

```json
{
    "firstName":"Harsh",
    "lastName":"Jain"
}
```

---

## Step 6

Click

```
Send
```

---

## Response

```json
{
    "id":1,
    "firstName":"Harsh",
    "lastName":"Jain"
}
```

Status

```
201 Created
```

(or 200 OK depending on your implementation)

---

# Internal Flow of POST Request

```
Postman

↓

POST Request

↓

DispatcherServlet

↓

StudentController

↓

@RequestBody

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

# Testing GET API

Controller:

```java
@GetMapping("/student/{studentId}")
```

Method:

```
GET
```

URL:

```
http://localhost:8080/student/1
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

# Internal Flow of GET Request

```
Postman

↓

GET Request

↓

Controller

↓

Service

↓

Repository

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

# Testing PUT API

Controller:

```java
@PutMapping("/student/{studentId}")
```

Method:

```
PUT
```

URL:

```
http://localhost:8080/student/1
```

Body:

```json
{
    "id":1,
    "firstName":"Rahul",
    "lastName":"Sharma"
}
```

Response:

```json
{
    "id":1,
    "firstName":"Rahul",
    "lastName":"Sharma"
}
```

---

# Internal Flow of PUT Request

```
Postman

↓

PUT Request

↓

Controller

↓

Service

↓

Repository

↓

Hibernate

↓

UPDATE Query

↓

Database
```

---

# Testing DELETE API

Controller:

```java
@DeleteMapping("/student/{studentId}")
```

Method:

```
DELETE
```

URL:

```
http://localhost:8080/student/1
```

Response:

```
204 No Content
```

or

```
200 OK
```

depending on your implementation.

---

# Headers

Headers provide additional information with the request.

Most common header:

```
Content-Type

application/json
```

Meaning:

The request body is JSON.

---

# Why Content-Type is Important?

Without it,

Spring Boot may not understand the request body.

Example:

Wrong

```
text/plain
```

Correct

```
application/json
```

---

# Request Body

Body contains the actual data sent to the server.

Example:

```json
{
    "firstName":"Harsh",
    "lastName":"Jain"
}
```

Spring converts this JSON into a Java Object using Jackson.

```
JSON

↓

Jackson

↓

Student Object
```

---

# Path Variable

Example URL:

```
/student/5
```

Controller:

```java
@GetMapping("/student/{studentId}")
public Student getStudent(
@PathVariable long studentId)
```

Spring extracts

```
5
```

and assigns it to

```java
studentId
```

---

# Query Parameters

Example:

```
GET

/student?firstName=Harsh
```

Controller:

```java
@GetMapping("/student")
public Student search(
@RequestParam String firstName){

}
```

Difference:

Path Variable

```
/student/5
```

Query Parameter

```
/student?firstName=Harsh
```

---

# JSON Response

Spring Boot automatically converts Java Objects into JSON.

Example:

Java Object

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

This conversion is done using

```
Jackson Library
```

---

# Common HTTP Status Codes

| Status Code | Meaning |
|-------------|---------|
|200 OK|Request Successful|
|201 Created|Resource Created Successfully|
|204 No Content|Delete Successful (No Response Body)|
|400 Bad Request|Invalid Request|
|401 Unauthorized|Authentication Required|
|403 Forbidden|Permission Denied|
|404 Not Found|Resource Not Found|
|405 Method Not Allowed|Wrong HTTP Method|
|500 Internal Server Error|Server Side Error|

---

# Common Errors in Postman

## Wrong HTTP Method

Wrong:

```
GET

/student/add
```

Correct:

```
POST

/student/add
```

---

## Wrong URL

Wrong:

```
localhost:8080/student
```

Correct:

```
localhost:8080/student/add
```

---

## Missing Request Body

POST request without JSON body.

Result:

```
400 Bad Request
```

---

## Wrong Content-Type

Wrong:

```
text/plain
```

Correct:

```
application/json
```

---

## Invalid JSON

Wrong:

```json
{
firstName:"Harsh"
}
```

Correct:

```json
{
    "firstName":"Harsh"
}
```

---

# Best Practices

✔ Always use the correct HTTP Method.

✔ Use meaningful API endpoints.

✔ Send proper JSON.

✔ Validate request data.

✔ Return appropriate HTTP Status Codes.

✔ Test every API before integrating with frontend.

---

# Real Project Flow

```
Frontend

↓

HTTP Request

↓

Postman (During Testing)

↓

Spring Boot Controller

↓

Service

↓

Repository

↓

Hibernate

↓

Database

↓

JSON Response

↓

Frontend/Postman
```

---

# Interview Questions

### What is Postman?

Postman is an API Testing Tool used to send HTTP requests and receive responses from REST APIs.

---

### Why do we use Postman?

To test backend APIs independently without creating a frontend.

---

### Difference between GET and POST?

GET is used to retrieve data.

POST is used to create new data.

---

### What is JSON?

JSON (JavaScript Object Notation) is a lightweight format used for exchanging data between client and server.

---

### What is the purpose of Headers?

Headers provide metadata about the request and response, such as Content-Type and Authorization.

---

### Difference between Path Variable and Query Parameter?

Path Variable is part of the URL.

Example:

```
/student/1
```

Query Parameter comes after `?`.

Example:

```
/student?firstName=Harsh
```

---

### Which library converts JSON into Java Objects in Spring Boot?

```
Jackson
```

---

### Which status code is returned when a resource is successfully created?

```
201 Created
```

---

# Summary

In this chapter, we learned:

- What Postman is.
- Why Postman is used.
- API testing process.
- HTTP Requests and Responses.
- GET, POST, PUT and DELETE requests.
- Headers.
- Request Body.
- Path Variables.
- Query Parameters.
- JSON conversion.
- HTTP Status Codes.
- Common mistakes.
- Interview questions.

---

# Quick Revision

```
Postman

↓

HTTP Request

↓

Spring Boot Controller

↓

Service

↓

Repository

↓

Hibernate

↓

Database

↓

JSON Response

↓

Postman
```

---

# Progress

- [x] Postman
- [x] API Testing
- [x] HTTP Request
- [x] HTTP Response
- [x] GET Request
- [x] POST Request
- [x] PUT Request
- [x] DELETE Request
- [x] Headers
- [x] Request Body
- [x] Query Parameters
- [x] Path Variables
- [x] JSON Response
- [x] HTTP Status Codes
- [x] Common Errors
- [x] Interview Questions