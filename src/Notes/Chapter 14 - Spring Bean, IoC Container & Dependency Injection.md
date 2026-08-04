# Chapter 14 - Spring Bean, IoC Container & Dependency Injection

> **Goal:** Understand what a Spring Bean is, how Spring creates and manages objects, what IoC (Inversion of Control) means, and how Dependency Injection works.

---

# Index

- What is a Bean?
- Why Do We Need Beans?
- Spring Container
- IoC (Inversion of Control)
- Dependency Injection (DI)
- Bean Lifecycle
- Bean Creation Process
- Stereotype Annotations
- @Component
- @Service
- @Repository
- @Controller
- @RestController
- @Autowired
- Constructor Injection
- Field Injection vs Constructor Injection
- Bean Scope
- Singleton vs Prototype
- Complete Flow
- Common Mistakes
- Interview Questions
- Summary

---

# Before Learning Bean

In Core Java, whenever we need an object, we create it manually.

Example:

```java
StudentService service = new StudentService();
```

Here,

You are responsible for:

- Creating the object
- Managing the object
- Destroying the object

As the project grows, managing thousands of objects becomes difficult.

This is where Spring Framework helps.

---

# What is a Bean?

A **Bean** is simply an object that is:

- Created by Spring
- Managed by Spring
- Stored inside the Spring Container
- Destroyed by Spring when the application stops

Simple Definition:

> A Bean is a Java object managed by the Spring Framework.

---

# Normal Java vs Spring Bean

## Core Java

```java
StudentService service =
new StudentService();
```

Developer creates the object.

---

## Spring Boot

```java
@Service
public class StudentService{

}
```

Spring creates the object automatically.

---

# Real Life Analogy

Suppose you want electricity in your house.

Do you generate electricity yourself?

No.

The electricity department generates and supplies it.

Similarly,

You don't create objects manually.

Spring creates and supplies them whenever required.

```
Developer

↓

Needs Object

↓

Spring Container

↓

Provides Object
```

---

# Why Do We Need Beans?

Without Spring:

```java
StudentRepository repository =
new StudentRepository();

StudentService service =
new StudentService(repository);
```

As the project grows:

```
50 Classes

↓

100 Objects

↓

200 Dependencies
```

Managing them manually becomes difficult.

Spring solves this problem.

---

# Spring Container

The Spring Container is the core of the Spring Framework.

Its responsibility is to:

- Create Beans
- Store Beans
- Inject Dependencies
- Manage Bean Lifecycle
- Destroy Beans

Think of it as a warehouse of objects.

```
Application Starts

↓

Spring Container

↓

Creates Beans

↓

Stores Beans

↓

Provides Beans Whenever Needed
```

---

# What is IoC?

IoC stands for:

```
Inversion of Control
```

---

# Before Spring

You control object creation.

```java
StudentService service =
new StudentService();
```

---

# With Spring

Spring controls object creation.

```java
@Service
public class StudentService{

}
```

Spring automatically creates the object.

Control shifts from:

```
Developer

↓

Spring Framework
```

This is called

```
Inversion of Control
```

---

# Dependency

Suppose

StudentController needs StudentService.

```
StudentController

↓

StudentService
```

StudentService is called a dependency of StudentController.

---

# What is Dependency Injection?

Dependency Injection means:

Instead of creating the required object manually,

Spring injects it automatically.

---

# Without Dependency Injection

```java
StudentService service =
new StudentService();
```

---

# With Dependency Injection

```java
private final StudentService service;

@Autowired
public StudentController(
StudentService service){

    this.service = service;

}
```

Spring automatically provides the object.

---

# Internal Working

```
Application Starts

↓

Spring Container

↓

Creates StudentService Bean

↓

Creates StudentController Bean

↓

Injects StudentService

↓

Application Ready
```

---

# Bean Creation Process

```
Application Starts

↓

Component Scan

↓

Finds Annotations

↓

Creates Bean

↓

Stores Bean

↓

Injects Dependencies

↓

Ready to Use
```

---

# What is Component Scanning?

Spring scans the project for classes having annotations like:

```
@Component

@Service

@Repository

@Controller

@RestController
```

Whenever Spring finds these annotations,

it creates Beans automatically.

---

# Stereotype Annotations

Spring provides special annotations to create Beans.

---

# @Component

```java
@Component
public class EmailUtil{

}
```

General-purpose Bean.

Used when a class does not belong to any specific layer.

---

# @Service

```java
@Service
public class StudentService{

}
```

Used for Business Logic.

---

# @Repository

```java
@Repository
public class StudentRepository{

}
```

Used for Database Layer.

It also provides database exception translation.

---

# @Controller

```java
@Controller
public class HomeController{

}
```

Used in Spring MVC applications.

Returns HTML pages.

---

# @RestController

```java
@RestController
public class StudentController{

}
```

Used in REST APIs.

Returns JSON data.

---

# Relationship Between These Annotations

```
@Component

│

├── @Service

├── @Repository

└── @Controller

        │

        └── @RestController
```

All of them create Beans.

---

# @Autowired

Used for Dependency Injection.

Example:

```java
@Autowired
private StudentService service;
```

Spring injects the required Bean automatically.

---

# Constructor Injection

Recommended approach.

Example:

```java
private final StudentService service;

public StudentController(
StudentService service){

    this.service = service;

}
```

If only one constructor exists,

`@Autowired` is optional.

---

# Why Constructor Injection?

Advantages:

- Better Testing
- Immutable Dependencies
- No Null Objects
- Recommended by Spring Team

---

# Field Injection

Example:

```java
@Autowired
private StudentService service;
```

Works,

but not recommended for large applications.

---

# Constructor Injection vs Field Injection

| Constructor Injection | Field Injection |
|-----------------------|----------------|
|Recommended|Not Recommended|
|Easy Testing|Difficult Testing|
|Supports final|Doesn't support final|
|Immutable|Mutable|

---

# Bean Scope

Bean Scope defines:

How many objects Spring should create.

---

# Singleton Scope

Default scope.

Only one object exists.

```
Application

↓

One Bean

↓

Shared Everywhere
```

Example:

```
StudentService

↓

One Object
```

---

# Prototype Scope

New object every time.

```java
@Scope("prototype")
```

Flow:

```
Request 1

↓

New Object

-----------------

Request 2

↓

New Object
```

---

# Bean Lifecycle

```
Application Starts

↓

Bean Created

↓

Dependencies Injected

↓

Bean Ready

↓

Bean Used

↓

Application Stops

↓

Bean Destroyed
```

---

# Complete Flow

```
Application Starts

↓

Spring Container

↓

Component Scan

↓

Creates Beans

↓

Stores Beans

↓

Dependency Injection

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

# Real Example From Your Project

Your Controller:

```java
@RestController
public class StudentController{

    private final StudentService studentService;

}
```

Your Service:

```java
@Service
public class StudentService{

    private final StudentRepository repository;

}
```

Your Repository:

```java
public interface StudentRepository
extends JpaRepository<Student, Long>{

}
```

Application Start:

```
Spring Boot Starts

↓

Creates Repository Bean

↓

Creates Service Bean

↓

Injects Repository

↓

Creates Controller Bean

↓

Injects Service

↓

Application Ready
```

Notice:

You never wrote:

```java
new StudentService();

new StudentRepository();
```

Spring did everything automatically.

---

# Common Mistakes

## Creating Objects Manually

Wrong

```java
StudentService service =
new StudentService();
```

Use Dependency Injection.

---

## Using Field Injection Everywhere

Prefer Constructor Injection.

---

## Forgetting Stereotype Annotation

Without

```java
@Service
```

Spring won't create the Bean.

---

## Circular Dependency

Example:

```
Service A

↓

Service B

↓

Service A
```

Avoid such design.

---

# Interview Questions

### What is a Spring Bean?

A Java object managed by the Spring Container.

---

### What is the Spring Container?

The core component of Spring responsible for creating, managing, and destroying Beans.

---

### What is IoC?

Inversion of Control means Spring controls object creation instead of the developer.

---

### What is Dependency Injection?

Dependency Injection is the process of providing required objects automatically instead of creating them manually.

---

### What is the difference between IoC and DI?

- IoC is the principle where control is transferred to Spring.
- DI is the technique Spring uses to implement IoC.

---

### Difference between @Component and @Service?

`@Service` is a specialized form of `@Component` used for the business layer.

---

### Difference between @Repository and @Service?

- `@Repository` handles database operations.
- `@Service` contains business logic.

---

### Difference between @Controller and @RestController?

- `@Controller` returns HTML Views.
- `@RestController` returns JSON responses.

---

### Which Dependency Injection is recommended?

Constructor Injection.

---

### What is the default Bean Scope?

Singleton.

---

### Can there be multiple Beans of the same class?

Yes.

Using different Bean definitions or Prototype Scope.

---

# Summary

In this chapter, we learned:

- What a Bean is.
- Why Beans are required.
- Spring Container.
- IoC.
- Dependency Injection.
- Component Scanning.
- Bean Lifecycle.
- Bean Creation Process.
- Stereotype Annotations.
- @Autowired.
- Constructor Injection.
- Bean Scopes.
- Complete internal working.

---

# Quick Revision

```
Application Starts

↓

Spring Container

↓

Component Scan

↓

Creates Beans

↓

Stores Beans

↓

Injects Dependencies

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

# Progress

- [x] Bean
- [x] Spring Container
- [x] IoC
- [x] Dependency Injection
- [x] Component Scan
- [x] @Component
- [x] @Service
- [x] @Repository
- [x] @Controller
- [x] @RestController
- [x] @Autowired
- [x] Constructor Injection
- [x] Bean Scope
- [x] Bean Lifecycle
- [x] Interview Questions