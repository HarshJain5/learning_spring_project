# Database Connection (Spring Boot)

> This README covers only the database connection setup in Spring Boot.

---

# Database Used

**Tutorial Database:** PostgreSQL

**Alternative Database:** MySQL

---

# Why PostgreSQL?

The tutorial uses PostgreSQL, so we will use PostgreSQL to avoid configuration differences.

Along with PostgreSQL, MySQL configuration will also be noted for comparison.

---

# PostgreSQL Dependency

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

# MySQL Dependency

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

# application.properties

Database connection details are configured inside:

```
src
 └── main
      └── resources
           └── application.properties
```

---

# PostgreSQL Configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
```

---

# MySQL Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

# Understanding Each Property

## spring.datasource.url

Specifies the database connection URL.

Example:

```
jdbc:postgresql://localhost:5432/studentdb
```

Breakdown:

| Part | Meaning |
|------|---------|
| jdbc | Java Database Connectivity |
| postgresql | Database Type |
| localhost | Database Server |
| 5432 | Database Port |
| studentdb | Database Name |

---

## spring.datasource.username

Database username.

PostgreSQL

```
postgres
```

MySQL

```
root
```

---

## spring.datasource.password

Password of the database user.

Example

```
admin123
```

---

## spring.datasource.driver-class-name

Specifies which JDBC driver Spring Boot should use.

PostgreSQL

```
org.postgresql.Driver
```

MySQL

```
com.mysql.cj.jdbc.Driver
```

---

# Default Ports

| Database | Default Port |
|----------|--------------|
| PostgreSQL | 5432 |
| MySQL | 3306 |

---

# Connection Flow

```
Spring Boot Application
          │
          ▼
application.properties
          │
          ▼
JDBC Driver
          │
          ▼
Database Server
          │
          ▼
Database Connected
```

---

# PostgreSQL vs MySQL

| Feature | PostgreSQL | MySQL |
|---------|------------|--------|
| Default Port | 5432 | 3306 |
| Username | postgres | root |
| Driver | org.postgresql.Driver | com.mysql.cj.jdbc.Driver |
| JDBC URL | jdbc:postgresql:// | jdbc:mysql:// |

---

# Summary

- Added database dependency in `pom.xml`.
- Configured database connection in `application.properties`.
- Learned the purpose of each datasource property.
- Understood the difference between PostgreSQL and MySQL connection settings.

---

# Progress

- [x] Add Database Driver
- [x] Configure application.properties
- [x] Connect Spring Boot with Database

--- 

# Entity & Table Creation (JPA)

> This section covers how Java classes are mapped to database tables using JPA annotations.

---

# What is an Entity?

An **Entity** is a Java class that represents a table in the database.

- One Entity = One Database Table
- One Object = One Row (Record)

Example

```
Java Class
     │
     ▼
 Student.java

     │
     ▼

Database Table
     │
     ▼
student
```

---

# Student Entity

```java
package com.spring.first_spring_proj.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
}
```

> **Note:** In your code you wrote:

```java
@Column(name = "firt_name")
```

It should be

```java
@Column(name = "first_name")
```

---

# @Entity

Marks the class as a JPA Entity.

Without `@Entity`, Spring Boot will not create a table for this class.

Example

```java
@Entity
public class Student {

}
```

---

# @Table

Specifies the database table name.

```java
@Table(name = "student")
```

Generated Table

```
student
```

If `@Table` is omitted, JPA uses the class name as the table name by default.

---

# @Id

Marks the Primary Key.

Every table should have one primary key.

Example

```java
@Id
private long id;
```

---

# @GeneratedValue

Automatically generates the primary key value.

```java
@GeneratedValue(strategy = GenerationType.SEQUENCE)
```

Spring Boot asks the database to generate IDs automatically.

Example

```
1

2

3

4
```

No need to assign IDs manually.

---

# Generation Strategies

| Strategy | Description |
|----------|-------------|
| AUTO | JPA decides the best strategy |
| IDENTITY | Database auto-increment |
| SEQUENCE | Uses a database sequence (Recommended for PostgreSQL) |
| TABLE | Uses a separate table to generate IDs |

---

# PostgreSQL

Recommended

```java
@GeneratedValue(strategy = GenerationType.SEQUENCE)
```

---

# MySQL

Generally used

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

Because MySQL supports `AUTO_INCREMENT`.

---

# @Column

Maps a Java field to a database column.

Example

```java
@Column(name = "first_name")
private String firstName;
```

Java

```
firstName
```

↓

Database

```
first_name
```

---

# nullable = false

Means the column cannot store NULL values.

Example

```java
@Column(nullable = false)
```

Allowed

```
Harsh
```

Not Allowed

```
NULL
```

---

# Mapping Overview

| Java | Database |
|------|----------|
| Student Class | student Table |
| id | id |
| firstName | first_name |
| lastName | last_name |

---

# Entity Mapping Flow

```
Student.java

        │

        ▼

@Entity

        │

        ▼

@Table

        │

        ▼

Hibernate

        │

        ▼

PostgreSQL

        │

        ▼

student Table
```

---

# Expected Database Table

```
student

--------------------------------
id          BIGINT
first_name  VARCHAR
last_name   VARCHAR
--------------------------------
```

---

# Common Mistakes

### Wrong Column Name

❌

```java
@Column(name = "firt_name")
```

✅

```java
@Column(name = "first_name")
```

---

### Missing @Entity

Without `@Entity`, no table will be created.

---

### Missing @Id

Every Entity must have a Primary Key.

---

### Wrong Generation Strategy

- PostgreSQL → `SEQUENCE`
- MySQL → `IDENTITY`

---

# Interview Questions

### What is an Entity?

An Entity is a Java class that represents a database table.

---

### Why do we use @Entity?

To tell JPA that this class should be mapped to a database table.

---

### What is @Table?

It specifies the database table name.

---

### What is @Id?

It marks the Primary Key of the table.

---

### What is @GeneratedValue?

It automatically generates values for the primary key.

---

### Difference between SEQUENCE and IDENTITY?

- **SEQUENCE** uses a database sequence and is commonly used with PostgreSQL.
- **IDENTITY** uses auto-increment and is commonly used with MySQL.

---

# Summary

- Learned what an Entity is.
- Created the `Student` entity.
- Mapped the entity to the `student` table.
- Learned `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, and `@Column`.
- Understood the difference between PostgreSQL and MySQL ID generation strategies.

---

# Progress

- [x] Entity
- [x] Table Mapping
- [x] Primary Key
- [x] Auto ID Generation
- [x] Column Mapping

--- 
# Repository & Service Basics

> This section introduces the Repository and Service layers in a Spring Boot application.

---

# Spring Boot Architecture

```
Client (Browser/Postman)
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

Each layer has its own responsibility.

---

# What is Repository?

A **Repository** is responsible for communicating with the database.

It performs operations like:

- Save Data
- Fetch Data
- Update Data
- Delete Data

Instead of writing SQL queries manually, Spring Data JPA provides these operations automatically.

---

# Creating Repository

```java
package com.spring.first_spring_proj.repositories;

import com.spring.first_spring_proj.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

}
```

---

# Understanding the Code

```java
JpaRepository<Student, Long>
```

### Student

Represents the Entity (Table).

```
Student Entity

↓

student Table
```

---

### Long

Represents the data type of the Primary Key.

```java
private long id;
```

Since `id` is of type `long`, we use `Long`.

---

# What is JpaRepository?

`JpaRepository` is an interface provided by Spring Data JPA.

It already contains common database methods.

Example:

```java
save()

findAll()

findById()

delete()

deleteById()

count()
```

You don't have to write these methods yourself.

---

# What is Service?

A **Service** contains the business logic of the application.

It acts as a bridge between the Controller and the Repository.

```
Controller

↓

Service

↓

Repository

↓

Database
```

---

# Creating Service

```java
package com.spring.first_spring_proj.services;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

}
```

---

# What is @Service?

Marks the class as a Service Layer.

Spring automatically creates and manages its object (Bean).

---

# Why Do We Need a Service?

Instead of writing business logic inside the Controller, we keep it inside the Service.

Example:

❌ Bad Practice

```
Controller

↓

Business Logic

↓

Database
```

✅ Good Practice

```
Controller

↓

Service (Business Logic)

↓

Repository

↓

Database
```

This makes the project clean and easy to maintain.

---

# Difference Between Repository and Service

| Repository | Service |
|------------|---------|
| Talks to Database | Contains Business Logic |
| Uses JPA | Uses Repository |
| CRUD Operations | Processes Data |
| Data Access Layer | Business Layer |

---

# Flow

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

# Summary

- Repository is used to interact with the database.
- Service is used to write business logic.
- `JpaRepository` provides built-in CRUD methods.
- `@Service` marks a class as the service layer.
- Controller should call Service, and Service should call Repository.