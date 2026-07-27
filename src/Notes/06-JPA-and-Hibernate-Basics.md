# Chapter 06 - Entity & ORM Mapping

> **Goal:** Learn how Java Objects are mapped to Database Tables using JPA & Hibernate.

---

# Index

- What is ORM?
- Why do we need Entity?
- Entity Lifecycle
- @Entity
- @Table
- @Id
- @GeneratedValue
- GenerationType
- @Column
- @Getter
- @Setter
- @NotBlank
- Entity Flow
- PostgreSQL vs MySQL
- Common Mistakes
- Interview Questions
- Summary

---

# Before Learning Entity

Suppose we have a database table.

```
Student Table

+----+------------+-----------+
| ID | First Name | Last Name |
+----+------------+-----------+
| 1  | Harsh      | Jain      |
| 2  | Rahul      | Sharma    |
+----+------------+-----------+
```

How will Java understand this table?

Java understands **Objects**, not tables.

So we create

```java
Student student = new Student();
```

Now we need a bridge between

```
Java Object

↓

Database Table
```

This bridge is called **ORM**.

---

# What is ORM?

ORM stands for

> **Object Relational Mapping**

Object

↓

Java Class

Relational

↓

Database Table

Mapping

↓

Connecting both together

Example

```
Student Class

↓

Student Table

↓

Rows

↓

Objects
```

Without ORM

You write SQL manually.

With ORM

Hibernate writes SQL for you.

---

# Real Life Analogy

Suppose

You know only English.

Another person knows only Hindi.

You need a translator.

```
English

↓

Translator

↓

Hindi
```

Similarly

```
Java Object

↓

Hibernate

↓

Database Table
```

Hibernate acts as a translator.

---

# Our Entity

```java
@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank
    private String lastName;

}
```

This class represents the **student** table.

---

# @Entity

```java
@Entity
```

## What is it?

Marks a Java class as a JPA Entity.

Meaning

```
Java Class

↓

Database Table
```

Without `@Entity`

Spring ignores this class.

No table will be created.

---

# Internal Working

When Spring Boot starts

```
Application Starts

↓

Component Scan

↓

Hibernate Starts

↓

Finds @Entity

↓

Reads Fields

↓

Creates Table
```

---

# @Table

```java
@Table(name="student")
```

Used to specify the table name.

Without it

Hibernate automatically creates

```
Student

↓

student
```

depending on the naming strategy.

With it

```
@Table(name="student")
```

Table name becomes exactly

```
student
```

---

# Why use @Table?

Suppose

Java Class

```java
StudentDetails
```

But database table

```
student
```

Then

```java
@Table(name="student")
```

solves the mismatch.

---

# @Id

```java
@Id
```

Marks the Primary Key.

Every Entity should have one Primary Key.

Example

```
Student

↓

ID
```

Database

```
PRIMARY KEY
```

---

# Why Primary Key?

Without Primary Key

Hibernate cannot uniquely identify records.

Example

```
Harsh

Harsh

Harsh
```

Which one should it update?

Impossible.

That's why every Entity needs an ID.

---

# @GeneratedValue

```java
@GeneratedValue(...)
```

Automatically generates ID values.

Instead of

```java
student.setId(100);
```

Database generates

```
1

2

3

4

5
```

automatically.

---

# GenerationType

There are four strategies.

---

## AUTO

```java
@GeneratedValue(strategy = GenerationType.AUTO)
```

Hibernate chooses the best strategy automatically.

---

## IDENTITY

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

Database auto-increments the ID.

Mostly used in

- MySQL
- SQL Server

Example

```
1

2

3

4
```

---

## SEQUENCE

```java
@GeneratedValue(strategy = GenerationType.SEQUENCE)
```

Uses a database sequence.

Mostly preferred in PostgreSQL and Oracle.

Sequence example

```
student_seq

↓

1

2

3

4

5
```

This is why your tutorial uses

```java
GenerationType.SEQUENCE
```

because it uses PostgreSQL.

---

## TABLE

Stores generated IDs in a separate table.

Rarely used today.

---

# PostgreSQL vs MySQL

Your project currently uses PostgreSQL.

So

```java
@GeneratedValue(
    strategy = GenerationType.SEQUENCE
)
```

works perfectly.

If using MySQL,

normally we write

```java
@GeneratedValue(
    strategy = GenerationType.IDENTITY
)
```

Reason

MySQL mainly uses AUTO_INCREMENT instead of database sequences.

---

# @Column

Example

```java
@Column(
    name="first_name",
    nullable=false
)
```

Purpose

Map Java field to database column.

---

Without @Column

Hibernate creates

```
firstName
```

as the column.

With

```java
@Column(name="first_name")
```

Database column becomes

```
first_name
```

---

# nullable=false

```
nullable=false
```

Means

```
NULL

×

Not Allowed
```

Database enforces this constraint.

---

# Example

Allowed

```
Harsh
```

Not Allowed

```
NULL
```

---

# @Getter

Provided by Lombok.

Instead of writing

```java
public String getFirstName(){
    return firstName;
}
```

Lombok generates it automatically during compilation.

---

# @Setter

Instead of writing

```java
public void setFirstName(
        String firstName){

    this.firstName=firstName;
}
```

Lombok generates it automatically.

---

# Why Lombok?

Without Lombok

```java
100 lines
```

With Lombok

```java
10 lines
```

Cleaner code.

---

# @NotBlank

```java
@NotBlank
```

From

```
spring-boot-starter-validation
```

It validates String values.

Not allowed

```
NULL

""

"     "
```

Allowed

```
Harsh
```

---

# Difference

| Annotation | Allows NULL | Allows "" | Allows Spaces |
|------------|------------|-----------|---------------|
| @NotNull | ❌ | ✅ | ✅ |
| @NotEmpty | ❌ | ❌ | ✅ |
| @NotBlank | ❌ | ❌ | ❌ |

Interview favorite question.

---

# Entity Lifecycle

```
Create Object

↓

Student Entity

↓

Hibernate

↓

SQL Generated

↓

Database Table

↓

Record Saved
```

---

# Save Flow

```
Student Object

↓

studentRepository.save()

↓

Hibernate

↓

INSERT Query

↓

Database
```

You never write SQL manually.

Hibernate generates it.

---

# Hibernate Generated SQL

When you write

```java
studentRepository.save(student);
```

Hibernate internally generates something similar to:

```sql
INSERT INTO student
(id, first_name, last_name)
VALUES (?, ?, ?);
```

The `?` values are filled automatically.

---

# Common Mistakes

❌ Forgetting `@Entity`

No table created.

---

❌ Forgetting `@Id`

Application fails because Hibernate cannot identify the primary key.

---

❌ Using `SEQUENCE` in MySQL

Prefer `IDENTITY`.

---

❌ Forgetting Lombok plugin in IntelliJ

Code compiles incorrectly or IDE shows missing getter/setter errors.

---

❌ Missing Validation Dependency

`@NotBlank`

will not work.

---

# Interview Questions

### What is an Entity?

A Java class mapped to a database table using JPA.

---

### What is ORM?

Object Relational Mapping.

It maps Java Objects to Database Tables.

---

### Difference between Entity and Table?

Entity → Java Class

Table → Database Structure

---

### What does `@Entity` do?

Marks a Java class as a JPA Entity.

---

### Why do we use `@Table`?

To specify the database table name.

---

### What does `@Id` represent?

Primary Key.

---

### Difference between `IDENTITY` and `SEQUENCE`?

- `IDENTITY` → Uses AUTO_INCREMENT (commonly MySQL).
- `SEQUENCE` → Uses database sequences (commonly PostgreSQL/Oracle).

---

### Why use Lombok?

To reduce boilerplate code by automatically generating getters, setters, constructors, etc.

---

### Difference between `@NotNull`, `@NotEmpty`, and `@NotBlank`?

- `@NotNull` → Value cannot be `null`.
- `@NotEmpty` → Cannot be `null` or empty (`""`).
- `@NotBlank` → Cannot be `null`, empty, or only whitespace.

---

# Summary

In this chapter, we learned:

- What ORM is.
- Why Entities are needed.
- Purpose of `@Entity`.
- Role of `@Table`.
- Importance of `@Id`.
- ID generation strategies.
- How `@Column` maps fields.
- Why Lombok is useful.
- How validation with `@NotBlank` works.
- PostgreSQL vs MySQL differences.
- Hibernate's SQL generation.

---

# Quick Revision

```
Java Class
      │
      ▼
@Entity
      │
      ▼
Hibernate
      │
      ▼
SQL Generated
      │
      ▼
Database Table
```

---

# Progress

- [x] ORM
- [x] Entity
- [x] @Table
- [x] @Id
- [x] @GeneratedValue
- [x] GenerationType
- [x] @Column
- [x] Lombok
- [x] Validation
- [x] Entity Lifecycle
- [x] Interview Questions