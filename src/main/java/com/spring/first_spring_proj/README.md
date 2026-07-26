# Spring Boot Learning Journey 🚀

> **Goal:** Learn Spring Boot from scratch by building projects and maintaining complete notes.
>
> **Learning Source:** GeeksforGeeks Spring Boot Tutorial
>
> **Target:** After completing these notes, I should be able to build a Spring Boot project without watching any tutorial.

---

# Project 01 - First Spring Boot Project

---

# Prerequisites

- Core Java
- OOPs Concepts
- JDBC Basics
- Maven
- JDK 17+
- IntelliJ IDEA / Eclipse
- Postman
- MySQL or PostgreSQL

---

# Creating a Spring Boot Project

## Step 1

Open Spring Initializr

```
https://start.spring.io
```

---

## Step 2

Select

```
Project : Maven
```

---

## Step 3

Select

```
Language : Java
```

---

## Step 4

Select

```
Spring Boot Version : Latest Stable Version
```

---

## Step 5

Group

```
com.example
```

---

## Step 6

Artifact

```
demo
```

---

## Step 7

Package Name

```
com.example.demo
```

---

## Step 8

Packaging

```
Jar
```

---

## Step 9

Configuration File

```
Properties (.properties)
```

---

## Step 10

Java Version

```
17
```

---

## Step 11

Dependencies

### If Following Video

```
Spring Web
Lombok
PostgreSQL Driver
```

### If Using MySQL

```
Spring Web
Lombok
MySQL Driver
```

---

# Download Project

Extract the downloaded ZIP file.

Open the project in IntelliJ IDEA.

---

# Build Project

Run

```bash
mvn clean install
```

If everything is correct, Maven will show

```
BUILD SUCCESS
```

---

# Running the Spring Boot Application

### Using IntelliJ

Click the Run button.

OR

### Using Terminal

```bash
mvn spring-boot:run
```

---

Application starts on

```
http://localhost:8080
```

---

# Project Structure

```
src
│
├── main
│
│── java
│     │
│     └── com.spring.first_spring_proj
│
│           ├── controllers
│           │      └── MyController.java
│           │
│           └── FirstSpringProjApplication.java
│
│
└── resources
      │
      └── application.properties
```

---

# First REST Controller

```java
package com.spring.first_spring_proj.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/add_two_numbers")
    public int addTwoNumbers(
            @RequestParam int a,
            @RequestParam int b){

        return a+b;
    }

    @GetMapping("/mul_two_numbers")
    public int mulTwoNumbers(
            @RequestParam int a,
            @RequestParam int b){

        return a*b;
    }

}
```

---

# Code Explanation

## @RestController

Marks the class as a REST Controller.

Instead of returning HTML pages, it returns data directly.

Example

```
30
```

or

```json
{
   "id":1,
   "name":"Harsh"
}
```

---

## @GetMapping

Maps an HTTP GET request to a Java method.

Example

```java
@GetMapping("/hello")
public String hello(){
    return "Hello";
}
```

URL

```
http://localhost:8080/hello
```

Output

```
Hello
```

---

## @RequestParam

Reads values from URL query parameters.

Example

```java
@RequestParam int a
```

Request

```
/add_two_numbers?a=10&b=20
```

Spring automatically converts

```
10
```

into

```java
int
```

---

# Testing APIs

### Browser

Only GET APIs can be tested directly.

Example

```
http://localhost:8080/add_two_numbers?a=10&b=20
```

Output

```
30
```

---

### Postman

Supports

- GET
- POST
- PUT
- PATCH
- DELETE

---

# HTTP Request Mapping Annotations

| Annotation | HTTP Method | Purpose |
|------------|-------------|----------|
| @GetMapping | GET | Read Data |
| @PostMapping | POST | Insert Data |
| @PutMapping | PUT | Replace Entire Data |
| @PatchMapping | PATCH | Update Partial Data |
| @DeleteMapping | DELETE | Delete Data |

---

# Request Flow

```
Browser/Postman

        │

        ▼

HTTP Request

        │

        ▼

@RestController

        │

        ▼

@GetMapping()

        │

        ▼

Java Method Executes

        │

        ▼

Returns Response

        │

        ▼

Browser/Postman
```

---

# What We Learned

✅ Spring Initializr

✅ Maven Project

✅ Spring Boot Setup

✅ REST Controller

✅ Get Mapping

✅ Request Parameters

✅ Running Spring Boot Project

✅ Testing APIs

---

# Database Choice

The tutorial uses

```
PostgreSQL
```

My project originally selected

```
MySQL
```

Both databases work perfectly with Spring Boot.

The only things that change are

- Driver
- JDBC URL
- Port Number
- Hibernate Dialect

Everything else remains the same.

---

# Recommendation

For this tutorial,

**Follow PostgreSQL exactly as shown in the video.**

Alongside every database-related topic, also learn the MySQL equivalent.

This way,

- You can follow the tutorial easily.
- You will also know how to build projects using MySQL.
- In interviews, switching between databases becomes easy.

---

# PostgreSQL vs MySQL

## Maven Dependency

### PostgreSQL

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

### MySQL

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

---

# JDBC URL

### PostgreSQL

```
jdbc:postgresql://localhost:5432/studentdb
```

### MySQL

```
jdbc:mysql://localhost:3306/studentdb
```

---

# Default Port

| Database | Port |
|-----------|------|
| PostgreSQL | 5432 |
| MySQL | 3306 |

---

# Driver Class

### PostgreSQL

```
org.postgresql.Driver
```

### MySQL

```
com.mysql.cj.jdbc.Driver
```

---

# application.properties

## PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
```

---

## MySQL

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

# Hibernate Dialect

### PostgreSQL

```
PostgreSQLDialect
```

### MySQL

```
MySQLDialect
```

---

# PostgreSQL vs MySQL Summary

| Feature | PostgreSQL | MySQL |
|----------|------------|--------|
| Default Port | 5432 | 3306 |
| JDBC URL | jdbc:postgresql:// | jdbc:mysql:// |
| Driver | org.postgresql.Driver | com.mysql.cj.jdbc.Driver |
| Default Username | postgres | root |
| Hibernate Dialect | PostgreSQLDialect | MySQLDialect |

---

# Interview Questions

### What is Spring Boot?

Spring Boot is a framework built on top of the Spring Framework that simplifies the development of Java applications by providing auto-configuration, embedded servers, and production-ready features.

---

### Why do we use @RestController?

It marks a class as a REST controller and automatically returns data (JSON, String, etc.) instead of rendering HTML views.

---

### What is @GetMapping?

`@GetMapping` maps an HTTP GET request to a specific Java method.

---

### What is @RequestParam?

It is used to read values from URL query parameters and bind them to method parameters.

---

### Which embedded server does Spring Boot use by default?

Tomcat.

---

### Which build tools are supported by Spring Boot?

- Maven
- Gradle

---

# Common Mistakes

- Forgetting to add required dependencies.
- Using the wrong Java version.
- Using an incorrect server port.
- Typing the wrong URL in the browser.
- Forgetting `@RestController`.
- Forgetting `@RequestParam`.
- Not rebuilding the project after changing dependencies.
- Using the wrong database driver.
- Mixing PostgreSQL and MySQL configuration properties.

---

# Learning Strategy

For every new topic, maintain notes in the following format:

```
Topic

↓

Concept

↓

Code

↓

Explanation

↓

Working Flow

↓

ASCII Diagram

↓

Interview Questions

↓

Common Mistakes

↓

PostgreSQL Version

↓

MySQL Equivalent

↓

Summary
```

By the end of this learning journey, this README will become a complete Spring Boot handbook that can be used to build projects without referring back to tutorials.

---

# Progress Tracker

## Completed Topics

- [x] Spring Initializr
- [x] Maven Project Setup
- [x] Spring Boot Project Creation
- [x] REST Controller
- [x] @GetMapping
- [x] @RequestParam
- [x] Running Spring Boot Application
- [x] Testing APIs
- [x] PostgreSQL vs MySQL Basics

---

**Next Topic:** Spring Boot Architecture / Starter Dependencies (according to the tutorial)