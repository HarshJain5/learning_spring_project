# Chapter 02 - Creating Your First Spring Boot Project

> **Goal:** Learn how to create, configure, import, build, and run a Spring Boot project.

---

# Index

- Why Do We Need Spring Initializr?
- What is Spring Initializr?
- Project Creation
- Understanding Every Field
- Dependencies
- Importing into IntelliJ IDEA
- Maven Build
- Project Structure
- Running the Application
- First REST API
- Common Errors
- Interview Questions
- Summary

---

# Why Do We Need Spring Initializr?

Creating a Spring project manually requires:

- Downloading Spring libraries
- Configuring Maven or Gradle
- Creating project structure
- Adding configuration files
- Managing dependencies

This process is time-consuming.

Spring Initializr automatically creates a ready-to-use Spring Boot project.

---

# What is Spring Initializr?

Spring Initializr is an official web tool that generates a Spring Boot project with the required configuration and dependencies.

It creates:

- Maven/Gradle project
- Project structure
- `pom.xml`
- `application.properties`
- Main application class
- Required dependencies

Official Website:

https://start.spring.io

---

# Creating a Spring Boot Project

Fill the following details:

| Field | Value |
|--------|-------|
| Project | Maven |
| Language | Java |
| Spring Boot | Latest Stable Version |
| Group | `com.spring` |
| Artifact | `first-spring-proj` |
| Name | `first-spring-proj` |
| Package Name | `com.spring.first_spring_proj` |
| Packaging | Jar |
| Java | 17 |

---

# Understanding Every Field

## Project

Available options:

- Maven
- Gradle

### Why Maven?

Maven manages:

- Dependencies
- Build process
- Packaging
- Plugins

We'll use Maven throughout this handbook.

---

## Language

We choose:

```
Java
```

because Spring Boot is primarily developed using Java.

---

## Spring Boot Version

Always prefer the latest **stable** version for learning.

Avoid milestones (`M`) or release candidates (`RC`) unless specifically required.

---

## Group

Example:

```
com.spring
```

It represents the organization's base package.

Similar to:

```
com.google

com.amazon

com.microsoft
```

---

## Artifact

Example:

```
first-spring-proj
```

This becomes the project name and JAR file name.

Generated JAR:

```
first-spring-proj-0.0.1-SNAPSHOT.jar
```

---

## Package Name

Example:

```
com.spring.first_spring_proj
```

This is the base package from where Spring starts scanning components.

---

## Packaging

### Jar (Recommended)

```
Application

↓

Embedded Tomcat

↓

Run Directly
```

Most Spring Boot projects use JAR packaging.

---

### War

WAR files are mainly used when deploying to an external application server.

For beginners, use **JAR**.

---

## Java Version

Use Java 17.

Why?

- Long-Term Support (LTS)
- Stable
- Widely used in industry
- Supported by modern Spring Boot versions

---

# Dependencies

For your project, we selected:

## Spring Web

Used for:

- REST APIs
- Controllers
- HTTP Requests
- Embedded Tomcat

Without it, `@RestController` and mapping annotations won't work.

---

## Lombok

Reduces boilerplate code by generating:

- Getters
- Setters
- Constructors
- `toString()`
- `equals()`
- `hashCode()`

We'll use it later with annotations like:

```java
@Getter
@Setter
```

---

## PostgreSQL Driver (Tutorial)

Allows Spring Boot to connect with PostgreSQL.

Equivalent MySQL dependency:

```
MySQL Driver
```

Only one database driver is usually required in a project.

---

## Validation

Provides validation annotations like:

- `@Valid`
- `@NotBlank`
- `@Email`
- `@Size`

We'll study these in a later chapter.

---

# Download and Extract

After clicking **Generate**:

Spring Initializr downloads a ZIP file.

Extract it to your desired location.

Example:

```
D:\SpringBoot\first-spring-proj
```

---

# Import into IntelliJ IDEA

Open IntelliJ.

Choose:

```
Open

↓

Select Project Folder

↓

Open as Maven Project
```

IntelliJ automatically downloads Maven dependencies.

---

# Project Structure

```
first-spring-proj
│
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   │
│   └── test
│
├── pom.xml
│
└── mvnw
```

We'll understand each folder in the next chapter.

---

# Build the Project

Open Terminal.

Run:

```bash
mvn clean install
```

---

# What does `mvn clean install` do?

## clean

Deletes the old build files.

```
target/

↓

Deleted
```

---

## install

- Compiles Java code
- Runs tests (if any)
- Creates the JAR
- Installs it into the local Maven repository

If successful, you'll see:

```
BUILD SUCCESS
```

---

# Running the Project

You can run the project in two ways.

### Method 1

Run the main class from IntelliJ.

```java
public static void main(String[] args) {
    SpringApplication.run(...);
}
```

---

### Method 2

Using Maven:

```bash
mvn spring-boot:run
```

---

# What Happens When the Project Starts?

```
main()

↓

SpringApplication.run()

↓

Spring Container Starts

↓

Beans Created

↓

Embedded Tomcat Starts

↓

Port 8080 Opened

↓

Application Ready
```

---

# First Controller

```java
@RestController
public class MyController {

    @GetMapping("/")
    public String hello() {
        return "Server Started";
    }

}
```

---

# Testing the Application

Open Browser:

```
http://localhost:8080/
```

Response:

```
Server Started
```

Congratulations! 🎉

Your first Spring Boot application is running.

---

# Common Errors

## BUILD FAILED

Possible reasons:

- Java version mismatch
- Maven not installed
- Internet unavailable
- Incorrect dependency

---

## Port 8080 Already in Use

Reason:

Another application is already using port 8080.

Solution:

Stop the existing application or change the server port in:

```properties
server.port=8081
```

---

## Dependencies Not Downloaded

Solution:

Reload Maven Project or run:

```bash
mvn clean install
```

---

## Main Class Not Found

Ensure your main class contains:

```java
@SpringBootApplication
```

and a valid `main()` method.

---

# Interview Questions

### What is Spring Initializr?

A tool used to generate a ready-to-use Spring Boot project with required dependencies and configuration.

---

### Why do we use Maven?

Maven manages dependencies, builds, plugins, testing, and packaging.

---

### What is the difference between JAR and WAR?

- **JAR**: Runs with an embedded server (recommended for Spring Boot).
- **WAR**: Deployed to an external application server.

---

### What does `mvn clean install` do?

- Deletes old build files.
- Compiles the project.
- Runs tests.
- Creates the JAR.
- Installs it into the local Maven repository.

---

### Which embedded server does Spring Boot use by default?

Apache Tomcat.

---

# Summary

In this chapter, we learned:

- How to create a Spring Boot project.
- Purpose of every Spring Initializr field.
- Why Maven is used.
- How to import the project into IntelliJ.
- How to build the project.
- How to run the application.
- How to create the first REST endpoint.
- Common setup errors and solutions.

---

# Quick Revision

```
Spring Initializr
        │
        ▼
Generate Project
        │
        ▼
Import into IntelliJ
        │
        ▼
mvn clean install
        │
        ▼
Run Application
        │
        ▼
Embedded Tomcat Starts
        │
        ▼
http://localhost:8080/
```

---

# Progress

- [x] Spring Initializr
- [x] Maven Project
- [x] Dependencies
- [x] IntelliJ Import
- [x] Project Build
- [x] Run Application
- [x] First REST API
- [x] Common Errors
- [x] Interview Questions