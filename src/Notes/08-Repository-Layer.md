# Chapter 08 - Service Layer in Spring Boot

> **Goal:** Understand why Service Layer exists, how it works, Dependency Injection, @Service, Optional, Lambda Expression, and Custom Exceptions.

---

# Index

- What is Service Layer?
- Why do We Need Service?
- Controller vs Service vs Repository
- @Service Annotation
- StudentService Class Explanation
- Constructor Injection
- @Autowired
- final Keyword
- Business Logic
- Optional Class
- orElseThrow()
- Lambda Expression
- Custom Exception
- Complete Request Flow
- Common Mistakes
- Interview Questions
- Summary

---

# What is Service Layer?

Service Layer contains the **business logic** of the application.

It acts as a bridge between:

```
Controller

↓

Service

↓

Repository
```

---

# Why Do We Need Service?

Suppose we directly write database code inside Controller.

Example:

```java
@PostMapping("/student/add")
public Student addStudent(Student student){

    return studentRepository.save(student);

}
```

This works.

But problem:

Controller now handles:

- HTTP Request
- Validation
- Business Logic
- Database Operation

Everything is mixed.

This violates clean architecture.

---

# Better Approach

Separate responsibilities.

```
Controller

Only handles API requests


Service

Handles business logic


Repository

Handles database operations

```

---

# Real Life Example

Bank Application:

```
Customer

↓

Bank Counter

↓

Bank Employee

↓

Database
```

Mapping:

| Real World | Spring Boot |
|-|-|
| Bank Counter | Controller |
| Employee | Service |
| Database Clerk | Repository |
| Database | Database |

---

# Controller vs Service vs Repository

| Layer | Responsibility |
|-|-|
| Controller | Receive HTTP Request |
| Service | Apply Business Rules |
| Repository | Communicate with Database |

---

# Our StudentService

Code:

```java
@Service
public class StudentService {

    private final StudentRepository studentRepository;

}
```

---

# @Service Annotation

```java
@Service
```

Tells Spring:

"This class contains business logic. Create its Bean."

Internally:

```
Application Starts

↓

Component Scan

↓

Find @Service

↓

Create Object

↓

Store Bean
```

---

# Without @Service

Spring will not create an object.

Then Controller cannot inject it.

You will get:

```
NoSuchBeanDefinitionException
```

---

# Constructor Injection

Your code:

```java
private final StudentRepository studentRepository;


@Autowired
public StudentService(
        StudentRepository studentRepository){

    this.studentRepository = studentRepository;

}
```

This is called Constructor Injection.

---

# Why Constructor Injection?

Because Service depends on Repository.

Relationship:

```
StudentService

needs

StudentRepository
```

Spring provides it automatically.

---

# What Happens Internally?

Application starts:

```
Spring Container

↓

Creates StudentRepository Bean

↓

Creates StudentService Bean

↓

Finds Constructor

↓

Injects Repository Object

↓

StudentService Ready
```

---

# @Autowired

```java
@Autowired
```

Used for Dependency Injection.

It tells Spring:

"Provide the required object here."

---

# Before Spring

We manually create:

```java
StudentRepository repo =
        new StudentRepository();
```

---

# With Spring

We only declare:

```java
private final StudentRepository repo;
```

Spring provides it.

---

# Is @Autowired Required?

Since Spring 4.3,

if a class has only one constructor:

```java
public StudentService(
StudentRepository repository){

}
```

then `@Autowired` is optional.

Spring automatically uses that constructor.

But many developers still write it for clarity.

---

# final Keyword

Your code:

```java
private final StudentRepository studentRepository;
```

Means:

Reference cannot be changed after initialization.

Example:

```java
studentRepository = repo1;

studentRepository = repo2; // Not Allowed
```

---

# Why Use final?

Benefits:

- Dependency cannot accidentally change.
- Makes class immutable.
- Works well with Constructor Injection.
- Better coding practice.

---

# addStudent() Method

Code:

```java
public Student addStudent(Student student){

    Student addedStudent =
            this.studentRepository.save(student);

    return addedStudent;
}
```

---

# Flow

```
Controller

↓

Student Object

↓

Service

↓

save()

↓

Repository

↓

Hibernate

↓

Database

```

---

# What save() Does?

```java
repository.save(student)
```

If ID does not exist:

```
INSERT
```

If ID exists:

```
UPDATE
```

---

# getStudentById()

Code:

```java
public Student getStudentById(long studentId){

    Optional<Student> optionalValue =
        studentRepository.findById(studentId);

}
```

---

# What is Optional?

Optional is a container class introduced in Java 8.

It represents:

```
Value Present

OR

Value Not Present
```

---

# Without Optional Problem

Old way:

```java
Student student =
repository.findById(id);

if(student==null){

}
```

Problem:

NullPointerException chances.

---

# With Optional

```java
Optional<Student> student;
```

Means:

Maybe Student exists.

Maybe not.

---

# Optional Flow

```
findById(10)

        |

        ▼

Optional<Student>

        |

        ▼

Student Found?

        |

   Yes       No

   |          |

Return     Exception
Student
```

---

# orElseThrow()

Code:

```java
return optionalValue.orElseThrow(
()-> new NotFoundExceptions(
"Student Not Found"
));
```

Meaning:

If value exists:

```
Return Student
```

Otherwise:

```
Throw Exception
```

---

# Lambda Expression

This part:

```java
() -> new NotFoundExceptions()
```

is Lambda Expression.

Java 8 feature.

---

# Normal Function

Without Lambda:

```java
new Supplier<Student>(){

    public Student get(){

    }

}
```

Too much code.

---

# Lambda

Short form:

```java
() -> {

}
```

---

# Breaking Lambda

```java
() -> new Exception()
```

Meaning:

```
Input

()

No Parameters


Arrow

->


Return

new Exception()
```

---

# Custom Exception

Your class:

```java
public class NotFoundExceptions
extends RuntimeException
{

}
```

---

# Why Create Custom Exception?

Default exceptions:

```
Exception
NullPointerException
RuntimeException
```

are generic.

Custom exceptions give meaningful errors.

Example:

```
Student with Id 10 Not Found
```

is much better.

---

# RuntimeException

Your class:

```java
extends RuntimeException
```

means:

This exception is unchecked.

No need:

```java
throws
```

everywhere.

---

# Complete API Flow

Example:

Request:

```
GET /student/5
```

---

Flow:

```
Browser/Postman

        |

        ▼

StudentController

        |

        ▼

getStudentById()

        |

        ▼

StudentService

        |

        ▼

studentRepository.findById()

        |

        ▼

Hibernate

        |

        ▼

Database

        |

        ▼

Student Object

        |

        ▼

JSON Response
```

---

# Complete Architecture

```
             Client

               |

               ▼

        Controller Layer

               |

               ▼

         Service Layer

               |

               ▼

       Repository Layer

               |

               ▼

          Hibernate

               |

               ▼

          Database
```

---

# Common Mistakes

❌ Writing SQL inside Service.

SQL belongs to Repository.

---

❌ Creating Repository object manually.

Wrong:

```java
new StudentRepository();
```

---

❌ Putting business logic in Controller.

Controller should remain thin.

---

❌ Using field injection:

```java
@Autowired
private StudentRepository repo;
```

Prefer constructor injection.

---

# Interview Questions

### What is Service Layer?

Service layer contains business logic and acts between Controller and Repository.

---

### Why do we need Service Layer?

To separate business logic from API and database code.

---

### What does @Service do?

Registers a class as a Spring Bean.

---

### Why use Constructor Injection?

For loose coupling, immutability, and better testing.

---

### What is Optional?

A container object that may contain a value or may be empty.

---

### Why use orElseThrow()?

To return value if present otherwise throw an exception.

---

### What is Lambda Expression?

A feature introduced in Java 8 to write functional interfaces in shorter form.

---

### Why create custom exceptions?

To provide meaningful application-specific error messages.

---

# Summary

In this chapter we learned:

- Purpose of Service Layer.
- Difference between Controller, Service, Repository.
- @Service annotation.
- Dependency Injection.
- Constructor Injection.
- @Autowired.
- final keyword.
- Optional.
- orElseThrow().
- Lambda Expression.
- Custom Exception.
- Complete request flow.

---

# Quick Revision

```
Controller

Receives Request

        ↓

Service

Business Logic

        ↓

Repository

Database Operation

        ↓

Hibernate

SQL Generation

        ↓

Database
```

---

# Progress

- [x] Service Layer
- [x] @Service
- [x] Constructor Injection
- [x] @Autowired
- [x] final keyword
- [x] Optional
- [x] orElseThrow()
- [x] Lambda Expression
- [x] Custom Exception
- [x] Request Flow
- [x] Interview Questions