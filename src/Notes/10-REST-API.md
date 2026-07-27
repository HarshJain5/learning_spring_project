# Chapter 10 - Complete CRUD API Implementation

> **Goal:** Implement complete CRUD operations in Spring Boot using Controller, Service, Repository, and understand ResponseEntity.

---

# Index

- What is CRUD?
- Create Operation
- Read Operation
- Update Operation
- Delete Operation
- Update API Flow
- Delete API Flow
- ResponseEntity
- HTTP Status Codes
- Service Methods
- Controller Methods
- Complete CRUD Architecture
- Common Mistakes
- Interview Questions
- Summary

---

# What is CRUD?

CRUD represents four basic database operations.

| Operation | HTTP Method | Purpose |
|---|---|---|
| Create | POST | Add new data |
| Read | GET | Fetch data |
| Update | PUT | Modify existing data |
| Delete | DELETE | Remove data |

---

# Complete Flow

```
Client

↓

Controller

↓

Service

↓

Repository

↓

Hibernate

↓

Database
```

---

# 1. CREATE Operation

## Controller

```java
@PostMapping("/student/add")
public Student addStudent(
        @RequestBody @Valid Student student){

    return this.studentService.addStudent(student);
}
```

---

## Flow

Request:

```json
{
"firstName":"Harsh",
"lastName":"Jain"
}
```

↓

Controller receives JSON

↓

@RequestBody converts JSON to Object

↓

Service called

↓

Repository save()

↓

Hibernate INSERT query

↓

Database

---

# Service

```java
public Student addStudent(Student student){

    return studentRepository.save(student);

}
```

---

# Generated SQL

Hibernate internally creates:

```sql
INSERT INTO student
(first_name,last_name)
VALUES
(?,?);
```

---

# 2. READ Operation

## Controller

```java
@GetMapping("/student/{sID}")
public Student getStudentById(
@PathVariable(name="sID") long studentId){

    return studentService.getStudentById(studentId);

}
```

---

# Service

```java
public Student getStudentById(long studentId){

    Optional<Student> optionalValue =
            studentRepository.findById(studentId);


    return optionalValue.orElseThrow(
            () -> new NotFoundExceptions(
            "Student Not Found")
    );

}
```

---

# Flow

```
GET /student/5

↓

Controller

↓

Service

↓

findById()

↓

Database

↓

Student Object

↓

JSON Response
```

---

# 3. UPDATE Operation

Update means modifying existing data.

Example:

Existing:

```json
{
"id":1,
"firstName":"Harsh",
"lastName":"Jain"
}
```

Change:

```json
{
"id":1,
"firstName":"Rahul",
"lastName":"Sharma"
}
```

---

# Controller

```java
@PutMapping("/student/{studentId}")
public Student updateStudent(
@PathVariable long studentId,
@RequestBody Student student){


    if(studentId != student.getId()){

        throw new RuntimeException(
        "ID mismatch");

    }


    return studentService.updateStudentById(student);

}
```

---

# Why Check ID?

URL:

```
/student/5
```

means:

Update student 5.

Body:

```json
{
"id":10
}
```

Mismatch.

Wrong data can be updated.

So we validate.

---

# Service Update Method

```java
public Student updateStudentById(
        Student student){

    return studentRepository.save(student);

}
```

---

# Why save() Updates?

Because JPA checks ID.

Example:

Existing:

```
ID = 1
```

Database:

```
Student id 1 exists
```

Then:

```
UPDATE
```

If ID doesn't exist:

```
INSERT
```

---

# Generated SQL

Hibernate:

```sql
UPDATE student
SET first_name=?,
last_name=?
WHERE id=?;
```

---

# 4. DELETE Operation

Delete removes data permanently.

---

# Controller

```java
@DeleteMapping("/student/{studentId}")
public void deleteStudentById(
@PathVariable long studentId){

    studentService.deleteStudentById(studentId);

}
```

---

# Service

```java
public void deleteStudentById(long studentId){

    studentRepository.deleteById(studentId);

}
```

---

# Generated SQL

Hibernate:

```sql
DELETE FROM student
WHERE id=?;
```

---

# Better Delete Method

Production style:

```java
public void deleteStudentById(long studentId){

    Student student =
    getStudentById(studentId);


    studentRepository.delete(student);

}
```

Why?

Because first we check:

Does student exist?

If not:

Throw Exception.

---

# ResponseEntity

Until now:

```java
return Student;
```

Spring automatically returns:

```
200 OK
```

But production APIs need control.

Example:

```
201 Created

404 Not Found

204 No Content
```

For this we use:

```
ResponseEntity
```

---

# What is ResponseEntity?

ResponseEntity represents the complete HTTP response.

It contains:

- Body
- Status Code
- Headers

---

# Without ResponseEntity

```java
public Student addStudent()
```

Only returns data.

---

# With ResponseEntity

```java
public ResponseEntity<Student> addStudent()
```

Returns:

```
Data + Status Code
```

---

# Example

```java
@PostMapping("/student/add")
public ResponseEntity<Student> addStudent(
@RequestBody Student student){

    Student saved =
    studentService.addStudent(student);


    return new ResponseEntity<>(
        saved,
        HttpStatus.CREATED
    );

}
```

---

# Response

Before:

```
200 OK
```

After:

```
201 CREATED
```

More correct REST behavior.

---

# Common HTTP Status Codes

| Code | Meaning |
|-|-|
|200|Success|
|201|Created|
|204|No Content|
|400|Bad Request|
|404|Not Found|
|500|Server Error|

---

# Complete CRUD Flow

```
                 Client

                   |

                   ▼

              Controller

                   |

                   ▼

               Service

                   |

                   ▼

             Repository

                   |

                   ▼

              Hibernate

                   |

                   ▼

              Database
```

---

# Complete Project Structure

```
first_spring_proj

│

├── controllers

│       └── StudentController

│

├── service

│       └── StudentService

│

├── repository

│       └── StudentRepository

│

├── entities

│       └── Student

│

└── exceptions

        └── NotFoundExceptions
```

---

# Common Mistakes

## 1. Updating Without Checking ID

Wrong:

```java
save(student);
```

directly.

Always verify ID.

---

## 2. Returning void from delete API

Better:

Return ResponseEntity.

Example:

```java
ResponseEntity<Void>
```

---

## 3. Handling Exceptions in Controller

Wrong:

```java
try{

}
catch(){

}
```

everywhere.

Later we will learn:

```
Global Exception Handler
```

---

## 4. Putting Business Logic in Repository

Repository only handles database.

---

# Interview Questions

### What are CRUD operations?

Create, Read, Update, Delete operations performed on data.

---

### Difference between PUT and POST?

POST creates a new resource.

PUT updates an existing resource.

---

### What does save() do?

It performs insert or update depending on primary key existence.

---

### Why use ResponseEntity?

To control HTTP response body, status code, and headers.

---

### Which annotation is used for update API?

```java
@PutMapping
```

---

### Which annotation is used for delete API?

```java
@DeleteMapping
```

---

### What happens internally when save() is called?

Repository → Hibernate → SQL Generation → Database.

---

# Summary

In this chapter we learned:

- Complete CRUD operations.
- Update API.
- Delete API.
- save() behavior.
- ResponseEntity.
- HTTP status codes.
- Complete project flow.

---

# Quick Revision

```
POST
 |
Create
 |
save()


GET
 |
Read
 |
findById()


PUT
 |
Update
 |
save()


DELETE
 |
Remove
 |
deleteById()
```

---

# Progress

- [x] Create API
- [x] Read API
- [x] Update API
- [x] Delete API
- [x] ResponseEntity
- [x] HTTP Status Codes
- [x] CRUD Flow
- [x] Interview Questions