# Chapter 04 - Spring Boot Architecture & Internal Working

> **Goal:** Understand how Spring Boot works internally, what happens when we run the application, and why Dependency Injection exists.

---

# Index

- What Happens When We Run a Spring Boot Application?
- Spring Boot Architecture
- What is a Spring Container?
- What is IoC (Inversion of Control)?
- What is a Bean?
- What is Dependency Injection (DI)?
- Types of Dependency Injection
- Constructor Injection
- Component Scanning
- Stereotype Annotations
- Complete Application Flow
- Real-Life Example
- Common Mistakes
- Interview Questions
- Summary

---

# Before Spring

Let's understand the problem first.

Suppose we have three classes.

```java
public class StudentRepository {

}
```

```java
public class StudentService {

    StudentRepository repository =
            new StudentRepository();

}
```

```java
public class StudentController {

    StudentService service =
            new StudentService();

}
```

---

# Problem

Every object is created manually.

```
StudentController

↓

new StudentService()

↓

new StudentRepository()
```

Imagine a project with

- 100 Controllers
- 150 Services
- 80 Repositories

Creating and managing every object manually becomes difficult.

This is called **Tight Coupling**.

---

# What is Tight Coupling?

Two classes are tightly coupled when one class directly creates another object using `new`.

Example

```java
StudentRepository repository =
        new StudentRepository();
```

Problems:

- Difficult to maintain
- Difficult to test
- Difficult to replace implementations
- Difficult to reuse

---

# Spring's Solution

Instead of creating objects ourselves,

Spring creates and manages objects.

```
Developer

↓

Writes Class

↓

Spring

↓

Creates Object

↓

Stores Object

↓

Provides Object Whenever Needed
```

This is the foundation of Spring.

---

# What Happens When We Run the Project?

We click

```
Run
```

Execution starts from

```java
public static void main(String[] args) {

    SpringApplication.run(
        FirstSpringProjApplication.class,
        args
    );

}
```

This single line performs many tasks internally.

---

# Internal Working

```
main()

      │

      ▼

SpringApplication.run()

      │

      ▼

Reads Configuration

      │

      ▼

Creates Spring Container

      │

      ▼

Scans Project

      │

      ▼

Finds Components

      │

      ▼

Creates Beans

      │

      ▼

Injects Dependencies

      │

      ▼

Starts Embedded Tomcat

      │

      ▼

Application Ready
```

---

# What is Spring Container?

The **Spring Container** is the core of the Spring Framework.

Its responsibilities are:

- Creating objects
- Managing objects
- Injecting dependencies
- Destroying objects (when required)

Think of it as an object manager.

---

# Real-Life Analogy

Imagine a library.

Without Spring:

Every student buys their own books.

```
Student

↓

Book Store

↓

Book
```

With Spring:

The library stores books.

Whenever a student needs one,

the library provides it.

```
Student

↓

Library

↓

Book
```

The library is like the Spring Container.

---

# What is IoC (Inversion of Control)?

IoC = **Inversion of Control**

Normally,

you control object creation.

Example:

```java
StudentService service =
        new StudentService();
```

With Spring,

Spring controls object creation.

You simply ask for the object.

```
Developer

↓

Needs Object

↓

Spring Provides Object
```

So,

**control is inverted** from the developer to the Spring Container.

---

# What is a Bean?

A **Bean** is simply an object that is created and managed by the Spring Container.

Normal Object

```java
StudentService service =
        new StudentService();
```

Spring Bean

```java
@Service
public class StudentService {

}
```

Spring creates the object automatically.

---

# Bean Lifecycle (Basic)

```
Class Found

↓

Object Created

↓

Stored in Container

↓

Used Wherever Needed

↓

Destroyed When Application Stops
```

---

# What is Dependency?

Suppose

```
StudentController

↓

Needs

↓

StudentService
```

StudentService is called a **Dependency** of StudentController.

Similarly,

```
StudentService

↓

Needs

↓

StudentRepository
```

StudentRepository is a dependency of StudentService.

---

# What is Dependency Injection?

Dependency Injection means

**providing required objects automatically instead of creating them manually.**

Instead of

```java
StudentService service =
        new StudentService();
```

Spring injects the object.

Example

```java
private final StudentService studentService;

@Autowired
public StudentController(
        StudentService studentService){

    this.studentService = studentService;

}
```

---

# Why Dependency Injection?

Benefits:

- Loose Coupling
- Easy Testing
- Easy Maintenance
- Better Reusability
- Cleaner Code

---

# Types of Dependency Injection

Spring supports:

## 1. Constructor Injection ✅ (Recommended)

```java
@Autowired
public StudentService(
        StudentRepository repository){

}
```

---

## 2. Setter Injection

```java
@Autowired
public void setRepository(
        StudentRepository repository){

}
```

---

## 3. Field Injection

```java
@Autowired
private StudentRepository repository;
```

Not recommended for production.

Constructor Injection is the preferred approach.

---

# Why Constructor Injection?

Advantages:

- Dependency becomes mandatory.
- Works well with `final`.
- Easy to unit test.
- Prevents NullPointerException due to missing dependencies.

Example:

```java
private final StudentRepository repository;
```

---

# Component Scanning

How does Spring know which classes should become Beans?

Answer:

Component Scanning.

Spring scans the package where the main application class exists and all its sub-packages.

Example:

```
com.spring.first_spring_proj

│

├── controller

├── service

├── repository

├── entities
```

Spring scans all these packages automatically.

---

# Stereotype Annotations

These annotations tell Spring:

"Create a Bean for this class."

---

## @Component

Generic Spring Bean.

---

## @Service

Marks Business Logic classes.

Example

```java
@Service
public class StudentService {

}
```

---

## @Repository

Marks Database layer.

Example

```java
@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long>{

}
```

---

## @RestController

Marks REST API Controller.

Example

```java
@RestController
public class StudentController{

}
```

---

# Complete Request Flow

```
Browser

     │

     ▼

DispatcherServlet

     │

     ▼

StudentController

     │

     ▼

StudentService

     │

     ▼

StudentRepository

     │

     ▼

Database

     │

     ▼

Response

     │

     ▼

Browser
```

We'll study DispatcherServlet in detail later.

---

# How Spring Finds StudentService

Your code

```java
@Service
public class StudentService{

}
```

Spring starts.

↓

Component Scan.

↓

Finds `@Service`.

↓

Creates Bean.

↓

Stores Bean in Spring Container.

↓

Whenever Controller asks,

Spring injects the same Bean.

---

# Why Don't We Use `new`?

Bad Practice

```java
StudentService service =
        new StudentService();
```

Good Practice

```java
@Autowired
public StudentController(
        StudentService service){

}
```

Because Spring manages the object's lifecycle.

---

# Common Mistakes

❌ Using `new` to create Service or Repository objects.

❌ Placing classes outside the base package.

❌ Forgetting stereotype annotations (`@Service`, `@Repository`, etc.).

❌ Using Field Injection in new projects when Constructor Injection is preferred.

---

# Interview Questions

### What is IoC?

IoC (Inversion of Control) means the responsibility of creating and managing objects is transferred from the developer to the Spring Container.

---

### What is a Spring Bean?

A Bean is an object that is created, managed, and maintained by the Spring Container.

---

### What is Dependency Injection?

Dependency Injection is the process of providing required objects automatically instead of creating them manually.

---

### Why is Constructor Injection preferred?

- Mandatory dependencies
- Immutable fields with `final`
- Better testing
- Cleaner design

---

### What is Spring Container?

The Spring Container creates, manages, injects, and destroys Beans.

---

### What is Component Scanning?

It is the process where Spring searches for classes annotated with stereotype annotations and registers them as Beans.

---

### Difference Between IoC and Dependency Injection?

- **IoC** is the overall principle where Spring controls object creation.
- **Dependency Injection** is one technique Spring uses to achieve IoC by providing required objects automatically.

---

# Summary

In this chapter, we learned:

- Why manual object creation is a problem.
- What Tight Coupling is.
- How Spring solves it.
- What the Spring Container does.
- What IoC means.
- What a Bean is.
- What Dependency Injection is.
- Types of Dependency Injection.
- Why Constructor Injection is recommended.
- How Component Scanning works.
- Purpose of `@Component`, `@Service`, `@Repository`, and `@RestController`.

---

# Quick Revision

```
Run Application
        │
        ▼
SpringApplication.run()
        │
        ▼
Spring Container Starts
        │
        ▼
Component Scanning
        │
        ▼
Beans Created
        │
        ▼
Dependencies Injected
        │
        ▼
Embedded Tomcat Starts
        │
        ▼
Application Ready
```

---

# Progress

- [x] Spring Container
- [x] IoC
- [x] Bean
- [x] Dependency
- [x] Dependency Injection
- [x] Constructor Injection
- [x] Component Scanning
- [x] Stereotype Annotations
- [x] Internal Working
- [x] Interview Questions