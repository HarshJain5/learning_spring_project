# Chapter 01 - Introduction to Spring Boot

> **Prerequisites**
>
> - Core Java
> - OOPs
> - Collections
> - Exception Handling
> - JDBC (Basic Knowledge)

---

# Index

- What is Spring?
- Why was Spring Created?
- Problems Before Spring
- What is Spring Boot?
- Why Spring Boot?
- Features of Spring Boot
- Spring vs Spring Boot
- Spring Boot Architecture
- How Spring Boot Works
- Advantages
- Real Life Analogy
- Interview Questions
- Summary
- Quick Revision

---

# What is Spring?

**Spring** is an open-source Java Framework used to develop Java applications easily.

It provides ready-made features like

- Dependency Injection (DI)
- IoC Container
- Database Connectivity
- REST API Development
- Security
- Transaction Management
- Microservices Support

Instead of writing everything manually, Spring provides reusable components.

---

# Why was Spring Created?

Earlier, Java Enterprise applications were mainly built using **J2EE (Java 2 Enterprise Edition)**.

Developers faced many problems such as:

- Large XML configuration
- Complex project setup
- Tight coupling between classes
- Difficult testing
- Too much boilerplate code
- Time-consuming development

Spring was created to solve these problems and simplify enterprise application development.

---

# Problems Before Spring

Imagine creating a Java web application without Spring.

You have to manually:

- Create objects using `new`
- Manage object dependencies
- Open database connections
- Close database connections
- Configure servlets
- Write large XML files

Example:

```java
StudentRepository repository = new StudentRepository();
StudentService service = new StudentService(repository);
StudentController controller = new StudentController(service);
```

As the project grows, manually creating and connecting objects becomes difficult.

---

# What is Spring Boot?

**Spring Boot** is built on top of the Spring Framework.

It removes most of the manual configuration required in Spring.

Spring Boot provides:

- Auto Configuration
- Embedded Server
- Starter Dependencies
- Production-ready features
- Easy project setup

In simple words:

> **Spring Boot = Spring + Auto Configuration + Embedded Server + Less Configuration**

---

# Spring vs Spring Boot

| Spring Framework | Spring Boot |
|------------------|-------------|
| Requires more configuration | Minimal configuration |
| External server required (Tomcat, etc.) | Embedded Tomcat included |
| Manual dependency management | Starter dependencies |
| More XML configuration | Mostly annotation-based |
| Slower project setup | Very fast project setup |

---

# Why Spring Boot?

Suppose you want to create a REST API.

Without Spring Boot:

- Configure server
- Configure XML
- Add multiple JARs
- Configure Dispatcher Servlet
- Configure application context

With Spring Boot:

- Create project
- Add dependencies
- Write Controller
- Run application

That's it.

---

# Features of Spring Boot

## 1. Auto Configuration

Spring Boot automatically configures many things based on the dependencies you add.

Example:

If you add the PostgreSQL driver, Spring Boot automatically prepares database configuration support.

---

## 2. Starter Dependencies

Instead of downloading many libraries manually, Spring Boot provides starter packages.

Example:

```xml
spring-boot-starter-web
```

This single dependency includes everything required to build REST APIs.

---

## 3. Embedded Server

No need to install Tomcat separately.

Spring Boot already includes an embedded web server.

Supported servers include:

- Tomcat (Default)
- Jetty
- Undertow

When you run the project:

```
mvn spring-boot:run
```

The server starts automatically.

---

## 4. Production Ready

Spring Boot provides features like:

- Health Checks
- Metrics
- Monitoring
- Logging

through Spring Boot Actuator.

---

## 5. Opinionated Defaults

Spring Boot chooses sensible default configurations so beginners can focus on writing business logic instead of configuration.

---

# Spring Boot Architecture

```
                 Client
                    │
                    ▼
             Spring Boot
                    │
                    ▼
              Controller
                    │
                    ▼
                Service
                    │
                    ▼
              Repository
                    │
                    ▼
                Database
```

Each layer has a specific responsibility.

We'll learn every layer in upcoming chapters.

---

# How Spring Boot Works

When you run your application:

```
main()

   │

   ▼

SpringApplication.run()

   │

   ▼

Spring Boot Starts

   │

   ▼

Creates Beans

   │

   ▼

Starts Embedded Tomcat

   │

   ▼

Scans Components

   │

   ▼

Application Ready
```

We'll study each step in detail later.

---

# Why Companies Prefer Spring Boot?

Because it helps developers:

- Build applications faster
- Reduce boilerplate code
- Improve maintainability
- Easily build REST APIs
- Develop Microservices
- Integrate with databases
- Deploy quickly

Many enterprise companies use Spring Boot for backend development.

---

# Real-Life Analogy

Imagine building a house.

### Without Spring Boot

You buy:

- Cement
- Bricks
- Doors
- Windows
- Pipes
- Wiring

Then assemble everything yourself.

It takes a lot of time.

---

### With Spring Boot

You get a ready-made construction kit.

Everything is already organized.

You only focus on designing your house.

Spring Boot does the setup work for you.

---

# Advantages

- Faster Development
- Less Configuration
- Easy Deployment
- Embedded Server
- Production Ready
- Easy Database Integration
- Easy REST API Development
- Large Community Support
- Suitable for Microservices

---

# Common Misconceptions

### Spring and Spring Boot are different frameworks.

❌ Wrong

Spring Boot is built on top of the Spring Framework.

---

### Spring Boot replaces Spring.

❌ Wrong

Spring Boot **uses** Spring internally.

---

### Spring Boot removes the need to learn Spring.

❌ Wrong

To understand Spring Boot deeply, you still need Spring concepts like:

- IoC
- Dependency Injection
- Beans
- Application Context

---

# Interview Questions

### What is Spring?

A Java framework used to build enterprise applications.

---

### What is Spring Boot?

Spring Boot is an extension of the Spring Framework that simplifies application development through auto-configuration, starter dependencies, and embedded servers.

---

### Is Spring Boot a replacement for Spring?

No.

Spring Boot is built on top of Spring and uses Spring internally.

---

### What are Starter Dependencies?

Predefined dependency bundles that include commonly used libraries for a specific purpose.

Example:

```xml
spring-boot-starter-web
```

---

### Why do we use Spring Boot?

To reduce configuration, speed up development, and build production-ready applications easily.

---

### Which embedded server does Spring Boot use by default?

Apache Tomcat.

---

### Can Spring Boot work without Tomcat?

Yes.

It also supports Jetty and Undertow.

---

# Summary

In this chapter, we learned:

- What Spring is.
- Why Spring was created.
- Problems with traditional Java development.
- What Spring Boot is.
- Features of Spring Boot.
- Spring vs Spring Boot.
- Spring Boot Architecture.
- Application startup flow.
- Advantages of Spring Boot.

---

# Quick Revision

```
Spring
│
├── Java Framework
├── Enterprise Applications
├── Dependency Injection
├── IoC Container
└── Modular

↓

Spring Boot

├── Built on Spring
├── Auto Configuration
├── Starter Dependencies
├── Embedded Tomcat
├── Less Configuration
└── Production Ready
```

---

# Progress

- [x] What is Spring?
- [x] Why Spring?
- [x] Problems Before Spring
- [x] What is Spring Boot?
- [x] Spring vs Spring Boot
- [x] Features
- [x] Architecture
- [x] Startup Flow
- [x] Interview Questions
- [x] Quick Revision