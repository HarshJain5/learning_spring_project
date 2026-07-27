# Chapter 05 - Database Connection & JPA Basics

> **Goal:** Understand how Spring Boot connects to a database, what JPA and Hibernate are, and how `application.properties` is used.

---

# Index

- Why Do We Need a Database?
- Traditional JDBC vs Spring Boot
- What is JPA?
- What is Hibernate?
- JPA vs Hibernate
- Database Drivers
- PostgreSQL vs MySQL
- application.properties
- Database Configuration
- Spring Boot Connection Flow
- Hikari Connection Pool
- Common Errors
- Interview Questions
- Summary

---

# Why Do We Need a Database?

Applications need a place to permanently store data.

Examples:

- Student Records
- Employee Details
- Products
- Orders
- Users
- Payments

Without a database,

all data will be lost after the application stops.

---

# Traditional JDBC

Suppose we want to save a Student.

Without Spring Boot:

```java
Connection connection =
DriverManager.getConnection(...);

PreparedStatement ps =
connection.prepareStatement(...);

ps.executeUpdate();

connection.close();
```

Developer has to:

- Open connection
- Create statement
- Execute query
- Handle exception
- Close connection

Imagine writing this code for every API.

It becomes repetitive.

---

# Problems with JDBC

- Too much boilerplate code
- Manual connection handling
- Manual SQL writing
- Difficult maintenance
- Error-prone
- Time consuming

Spring Boot solves these problems using JPA and Hibernate.

---

# What is JPA?

**JPA (Java Persistence API)** is a Java specification for mapping Java objects to database tables.

Important point:

> **JPA is NOT a framework.**

It only defines rules.

For example,

JPA says:

- How an Entity should look
- How tables should be mapped
- How primary keys should be handled
- How relationships should be created

But JPA itself cannot perform database operations.

---

# What is Hibernate?

Hibernate is the most popular implementation of JPA.

Think like this:

```
JPA

↓

Rules

↓

Hibernate

↓

Implementation
```

Hibernate actually performs:

- Insert
- Update
- Delete
- Select

operations.

---

# Real-Life Example

Imagine driving a car.

Traffic Rules

↓

JPA

Car

↓

Hibernate

Driver

↓

Developer

You follow traffic rules.

The car actually moves.

Similarly,

JPA defines rules,

Hibernate performs the work.

---

# JPA vs Hibernate

| JPA | Hibernate |
|------|-----------|
| Specification | Framework |
| Defines rules | Implements rules |
| Cannot work alone | Works using JPA |
| Vendor Independent | Developed by Hibernate Team |

---

# Why Hibernate?

Hibernate automatically generates SQL.

Instead of

```sql
INSERT INTO student...
```

you simply write

```java
studentRepository.save(student);
```

Hibernate converts it into SQL.

---

# Database Driver

Spring Boot communicates with databases through JDBC Drivers.

Without a driver,

Spring cannot understand how to communicate with the database.

---

# PostgreSQL Driver

Dependency:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

Default Port

```
5432
```

Username

```
postgres
```

---

# MySQL Driver

Dependency

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

Default Port

```
3306
```

Username

```
root
```

---

# PostgreSQL vs MySQL

| PostgreSQL | MySQL |
|------------|-------|
| Port 5432 | Port 3306 |
| pgAdmin | MySQL Workbench |
| Username postgres | Username root |
| Driver org.postgresql | mysql-connector-j |
| Excellent for complex queries | Excellent for web applications |

Both work perfectly with Spring Boot.

---

# application.properties

Spring Boot stores configuration here.

Location

```
src

└── main

    └── resources

          └── application.properties
```

---

# PostgreSQL Configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_db

spring.datasource.username=postgres

spring.datasource.password=password

spring.datasource.driver-class-name=org.postgresql.Driver
```

---

# MySQL Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db

spring.datasource.username=root

spring.datasource.password=password

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Only these properties change.

Everything else remains almost the same.

---

# Understanding Every Property

---

## spring.datasource.url

Example

```properties
jdbc:mysql://localhost:3306/student_db
```

Meaning

```
jdbc

↓

Java Database Connectivity

↓

mysql

↓

Database

↓

localhost

↓

Current Machine

↓

3306

↓

Port Number

↓

student_db

↓

Database Name
```

---

## username

```
root
```

Database login username.

---

## password

Database login password.

---

## driver-class-name

Tells Spring which JDBC Driver to use.

MySQL

```
com.mysql.cj.jdbc.Driver
```

PostgreSQL

```
org.postgresql.Driver
```

---

# JPA Properties

---

## show-sql

```properties
spring.jpa.show-sql=true
```

Prints SQL queries in the console.

Useful during development.

---

## ddl-auto

One of the most important properties.

```properties
spring.jpa.hibernate.ddl-auto=update
```

Possible values

### none

No table creation.

---

### create

Deletes old tables.

Creates new tables.

⚠ Data Lost.

---

### create-drop

Creates tables.

Deletes them when application stops.

Useful for testing.

---

### update ✅

Updates schema.

Does not delete existing data.

Most commonly used during development.

---

### validate

Checks whether tables already exist.

If not,

application fails.

---

# Naming Strategy

Suppose your Entity

```java
class Student
```

Hibernate converts it into

```
student
```

automatically.

---

# Spring Boot Database Connection Flow

```
Application Starts

        │

        ▼

Reads application.properties

        │

        ▼

Loads JDBC Driver

        │

        ▼

Creates DataSource

        │

        ▼

Creates Hikari Connection Pool

        │

        ▼

Hibernate Starts

        │

        ▼

Scans Entities

        │

        ▼

Creates / Updates Tables

        │

        ▼

Application Ready
```

---

# What is DataSource?

A DataSource is an object that knows:

- Database URL
- Username
- Password
- Driver

Spring creates it automatically.

---

# What is HikariCP?

Spring Boot does not create a new database connection for every request.

Instead,

it creates a pool of reusable connections.

```
Application

↓

Hikari Pool

↓

Connection 1

Connection 2

Connection 3

Connection 4
```

Whenever an API needs a connection,

Spring borrows one from the pool,

uses it,

and returns it.

This is much faster than creating a new connection every time.

---

# Why Connection Pool?

Without Pool

```
Request

↓

Create Connection

↓

Execute Query

↓

Close Connection
```

Repeated for every request.

Slow.

---

With Pool

```
Request

↓

Borrow Connection

↓

Execute Query

↓

Return Connection
```

Fast.

---

# Common Errors

## Failed to configure DataSource

Reason

Wrong database configuration.

---

## Access Denied

Wrong username or password.

---

## Connection Refused

Database server is not running.

---

## Unknown Database

Database has not been created.

---

## Driver Not Found

Database dependency missing in `pom.xml`.

---

## Table Not Created

Check

```properties
spring.jpa.hibernate.ddl-auto
```

---

# Interview Questions

### What is JPA?

A Java specification for Object Relational Mapping (ORM).

---

### Is JPA a Framework?

No.

It is a specification.

---

### What is Hibernate?

Hibernate is the most popular implementation of JPA.

---

### What is ORM?

ORM (Object Relational Mapping) maps Java Objects to Database Tables.

---

### Difference Between JPA and Hibernate?

JPA defines rules.

Hibernate implements those rules.

---

### Why do we need JDBC Driver?

To enable communication between the Java application and the database.

---

### What is HikariCP?

The default high-performance connection pool used by Spring Boot.

---

### Which property creates tables automatically?

```properties
spring.jpa.hibernate.ddl-auto
```

---

### Which value is commonly used during development?

```
update
```

---

# Summary

In this chapter, we learned:

- Why databases are needed.
- Problems with JDBC.
- What JPA is.
- What Hibernate is.
- Difference between JPA and Hibernate.
- PostgreSQL vs MySQL configuration.
- `application.properties`.
- DataSource.
- HikariCP.
- Hibernate table creation.
- Common errors.

---

# Quick Revision

```
Application

      │

      ▼

application.properties

      │

      ▼

Driver

      │

      ▼

DataSource

      │

      ▼

HikariCP

      │

      ▼

Hibernate

      │

      ▼

Database
```

---

# Progress

- [x] JDBC Problems
- [x] JPA
- [x] Hibernate
- [x] Database Driver
- [x] application.properties
- [x] DataSource
- [x] HikariCP
- [x] ddl-auto
- [x] Common Errors
- [x] Interview Questions