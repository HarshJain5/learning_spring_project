# Chapter 07 - Repository Layer (Spring Data JPA)

> **Goal:** Learn how Spring Boot communicates with the database using the Repository Layer and how Spring Data JPA generates SQL automatically.

---

# Index

- What is Repository?
- Why do we need Repository?
- Repository Pattern
- What is Spring Data JPA?
- StudentRepository
- @Repository
- JpaRepository
- Generic Types
- CRUD Methods
- Internal Working
- SQL Generation
- Complete Flow
- Common Mistakes
- Interview Questions
- Summary

---

# Before Repository

Suppose we want to save a student.

Without Spring Data JPA

```
Controller

↓

Service

↓

JDBC

↓

Connection

↓

PreparedStatement

↓

SQL

↓

Database
```

You have to write SQL manually.

Example

```java
String sql =
"INSERT INTO student(first_name,last_name)
VALUES (?,?)";
```

This becomes repetitive.

---

# Repository Layer

Repository Layer is responsible for communicating with the database.

It hides all database-related code from the Service Layer.

```
Controller

↓

Service

↓

Repository

↓

Database
```

Service never directly talks to the database.

Repository always acts as the middle layer.

---

# Why Do We Need Repository?

Imagine an Amazon application.

```
Customer

↓

Website

↓

Business Logic

↓

Database
```

Would Business Logic write SQL?

No.

A dedicated database layer is used.

In Spring Boot, that layer is Repository.

---

# Repository Pattern

Repository Pattern is a design pattern that separates database operations from business logic.

Instead of

```
Service

↓

SQL
```

we use

```
Service

↓

Repository

↓

Database
```

Advantages

- Clean Architecture
- Loose Coupling
- Easy Testing
- Easy Maintenance

---

# StudentRepository

```java
package com.spring.first_spring_proj.repository;

import com.spring.first_spring_proj.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

}
```

Looks very small.

But internally it provides more than 200 methods.

---

# Why Interface?

Notice

```java
public interface StudentRepository
```

There is no class.

No object.

No implementation.

Question

How is it working?

Answer

Spring Boot automatically creates the implementation at runtime.

---

# Internal Working

```
Application Starts

↓

Spring Data JPA

↓

Finds JpaRepository

↓

Generates Implementation

↓

Creates Bean

↓

Stores Bean in Spring Container
```

So even though you never wrote

```java
class StudentRepositoryImpl
```

Spring creates it automatically.

---

# @Repository

Usually we write

```java
@Repository
public class ...
```

But for

```java
JpaRepository
```

it is optional.

Spring automatically detects interfaces extending JpaRepository.

Still,

@Repository tells Spring

"This class/interface belongs to the Persistence Layer."

Benefits

- Exception Translation
- Bean Registration
- Better Readability

---

# What is JpaRepository?

```java
JpaRepository<Student, Long>
```

It is an interface provided by Spring Data JPA.

It already contains common database methods.

Example

```java
save()

findById()

findAll()

delete()

deleteById()

count()

existsById()
```

Instead of writing SQL,

we simply call these methods.

---

# Understanding Generic Types

```java
JpaRepository<Student, Long>
```

Let's break it.

First Generic

```
Student
```

Represents

Which Entity?

Answer

```
Student
```

Second Generic

```
Long
```

Represents

Type of Primary Key.

Our Entity

```java
@Id
private long id;
```

Therefore

```java
JpaRepository<Student, Long>
```

If ID were String

```java
private String studentId;
```

Then

```java
JpaRepository<Student, String>
```

---

# save()

```java
studentRepository.save(student);
```

Purpose

Insert or Update.

How?

If ID does not exist

↓

INSERT

If ID already exists

↓

UPDATE

So

```
save()

↓

Insert OR Update
```

---

# findById()

```java
studentRepository.findById(id);
```

Returns

```java
Optional<Student>
```

Why Optional?

Because data may or may not exist.

Example

ID = 1

Found

↓

Student

ID = 500

Not Found

↓

Empty Optional

---

# findAll()

```java
studentRepository.findAll();
```

Returns

```java
List<Student>
```

Equivalent SQL

```sql
SELECT * FROM student;
```

---

# deleteById()

```java
studentRepository.deleteById(id);
```

Equivalent SQL

```sql
DELETE
FROM student
WHERE id=?
```

---

# existsById()

```java
studentRepository.existsById(id);
```

Returns

```
true

or

false
```

Useful before deleting or updating records.

---

# count()

```java
studentRepository.count();
```

Equivalent SQL

```sql
SELECT COUNT(*)
FROM student;
```

Returns total number of records.

---

# Complete CRUD Methods

| Method | Purpose |
|---------|---------|
| save() | Insert / Update |
| findById() | Find by Primary Key |
| findAll() | Fetch all records |
| delete() | Delete object |
| deleteById() | Delete using ID |
| existsById() | Check existence |
| count() | Total Records |

---

# Internal Working of save()

Suppose you call

```java
studentRepository.save(student);
```

Internally

```
Student Object

↓

Repository

↓

Hibernate

↓

Entity Manager

↓

SQL Generated

↓

JDBC Driver

↓

Database
```

Generated SQL

```sql
INSERT INTO student
(first_name,last_name)
VALUES (?,?)
```

Notice

You never wrote SQL.

Hibernate generated it.

---

# Internal Working of findById()

```
Controller

↓

Service

↓

Repository

↓

EntityManager

↓

Hibernate

↓

SELECT Query

↓

Database

↓

Student Object

↓

Service

↓

Controller

↓

JSON Response
```

---

# Why Repository Does Not Contain Business Logic?

Repository should only perform database operations.

Example

Good

```java
save()

findAll()

delete()
```

Bad

```java
calculateFees()

sendEmail()

generateOTP()
```

Those belong in the Service Layer.

---

# Repository vs Service

| Repository | Service |
|------------|---------|
| Database Operations | Business Logic |
| Uses JPA | Uses Repository |
| CRUD | Validation |
| SQL Generation | Decision Making |

---

# Spring Data JPA Magic

Question

Where is the implementation of

```java
save()
```

?

You never wrote it.

Spring generates it dynamically using proxies.

So

```java
StudentRepository
```

is only an interface,

but Spring creates the implementation at runtime.

---

# Common Mistakes

❌ Writing business logic inside Repository.

---

❌ Using wrong Primary Key type.

Example

```java
JpaRepository<Student, String>
```

while ID is Long.

---

❌ Creating Repository using

```java
new StudentRepository();
```

Never do this.

Spring manages Repository Beans.

---

❌ Forgetting to extend JpaRepository.

Without it,

CRUD methods won't be available.

---

# Interview Questions

### What is Repository?

Repository is the persistence layer responsible for communicating with the database.

---

### What is Spring Data JPA?

A Spring module that simplifies database access by providing ready-made Repository interfaces.

---

### Why do we extend JpaRepository?

To get built-in CRUD operations without writing SQL manually.

---

### Why is StudentRepository an Interface?

Spring automatically generates its implementation at runtime.

---

### What do these generics represent?

```java
JpaRepository<Student, Long>
```

- Student → Entity Class
- Long → Primary Key Type

---

### Difference between Repository and Service?

Repository performs database operations.

Service contains business logic.

---

### Does save() only insert?

No.

It performs both Insert and Update.

---

### What does findById() return?

```java
Optional<Student>
```

because the requested record may or may not exist.

---

# Summary

In this chapter, we learned:

- Repository Layer
- Repository Pattern
- Spring Data JPA
- JpaRepository
- Generic Types
- CRUD Methods
- SQL Generation
- Internal Working
- Runtime Implementation
- Common Mistakes
- Interview Questions

---

# Quick Revision

```
Controller

      │

      ▼

Service

      │

      ▼

Repository

      │

      ▼

Hibernate

      │

      ▼

EntityManager

      │

      ▼

JDBC Driver

      │

      ▼

Database
```

---

# Progress

- [x] Repository Layer
- [x] Repository Pattern
- [x] Spring Data JPA
- [x] JpaRepository
- [x] CRUD Methods
- [x] SQL Generation
- [x] Generic Types
- [x] Internal Working
- [x] Interview Questions