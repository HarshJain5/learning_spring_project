# Chapter 15 - Monolithic Architecture & Microservices

> **Goal:** Understand Monolithic Architecture, why Microservices were introduced, their advantages and disadvantages, and when to use each architecture.

---

# Index

- What is Software Architecture?
- What is Monolithic Architecture?
- Structure of a Monolithic Application
- Advantages of Monolithic Architecture
- Disadvantages of Monolithic Architecture
- What are Microservices?
- Why Microservices?
- Structure of a Microservices Application
- Communication Between Microservices
- API Gateway
- Service Discovery (Introduction)
- Database in Microservices
- Advantages of Microservices
- Disadvantages of Microservices
- Monolithic vs Microservices
- Real-World Examples
- When to Use Which Architecture?
- Common Mistakes
- Interview Questions
- Summary

---

# What is Software Architecture?

Software Architecture defines how an application is designed and how its different parts communicate with each other.

It is the blueprint of an application.

Example:

```
User

↓

Frontend

↓

Backend

↓

Database
```

As applications grow, choosing the right architecture becomes very important.

---

# Why Do We Need Different Architectures?

Suppose you are creating a simple Student Management System.

Modules:

- Student
- Teacher
- Course
- Fees
- Authentication

Initially, everything can be placed in one project.

But what if the application grows?

Imagine adding:

- Notifications
- Email Service
- Payment Gateway
- Reports
- Analytics
- Chat System

Now managing one large project becomes difficult.

That's why different architectures exist.

---

# What is Monolithic Architecture?

A Monolithic Architecture is an architecture where the entire application is built as a single project.

Everything is inside one application.

```
Student Module

Teacher Module

Course Module

Payment Module

Authentication Module

↓

One Spring Boot Project

↓

One Database

↓

One Deployment
```

---

# Structure of a Monolithic Application

```
Client

↓

Spring Boot Project

│

├── Controller

├── Service

├── Repository

├── Security

├── Payment

├── Notification

├── Reporting

↓

Database
```

Everything is packaged into one application.

---

# Real Example

Your current Student Project is a Monolithic Application.

Project Structure:

```
first_spring_proj

│

├── controllers

├── entities

├── repository

├── service

├── exceptions

├── resources

└── application.properties
```

Everything exists inside one Spring Boot project.

---

# Deployment of Monolithic Application

```
Spring Boot Project

↓

Build

↓

JAR File

↓

Deploy

↓

Server
```

Only one application is deployed.

---

# Advantages of Monolithic Architecture

## 1. Easy to Develop

Everything is inside one project.

Easy to understand.

---

## 2. Easy to Debug

Since all modules are together, debugging is straightforward.

---

## 3. Easy Deployment

Only one JAR file is deployed.

---

## 4. Easy Testing

No communication between multiple services.

---

## 5. Good for Beginners

Perfect for:

- College Projects
- Learning Spring Boot
- Small Applications
- Startups

---

# Problems with Monolithic Architecture

As the application grows, problems begin.

---

## Problem 1 - Large Codebase

Suppose the project contains:

```
500 Controllers

300 Services

200 Repositories
```

Finding code becomes difficult.

---

## Problem 2 - Entire Application Must Be Redeployed

Suppose only the Payment Module changes.

Even then,

You must rebuild the entire application.

```
Small Change

↓

Complete Build

↓

Complete Deployment
```

---

## Problem 3 - Scaling Issue

Suppose:

Payment Module receives heavy traffic.

Student Module receives very little traffic.

Still,

You must scale the entire application.

```
Need More Payment Capacity

↓

Entire Application Scaled
```

Resources are wasted.

---

## Problem 4 - One Failure Can Affect Everything

Suppose Payment Module crashes.

Sometimes the entire application becomes unstable.

---

## Problem 5 - Technology Lock

Suppose your application is written in Java.

You want to build Analytics using Python.

In a Monolith,

This becomes difficult.

---

# What are Microservices?

Microservices Architecture divides one large application into multiple small independent services.

Each service performs only one specific task.

Example:

```
Authentication Service

↓

Student Service

↓

Course Service

↓

Payment Service

↓

Notification Service
```

Each service is an independent Spring Boot project.

---

# Why Were Microservices Introduced?

To solve Monolithic problems.

Instead of:

```
One Huge Project
```

We create:

```
Many Small Projects
```

Each project is responsible for only one business function.

---

# Structure of Microservices

```
Client

↓

API Gateway

│

├── Student Service

├── Course Service

├── Payment Service

├── Notification Service

├── Authentication Service

↓

Individual Databases
```

Each service works independently.

---

# Independent Deployment

Suppose only Student Service changes.

Only Student Service is rebuilt and deployed.

```
Student Service

↓

Build

↓

Deploy

↓

Done
```

Other services continue running.

---

# Independent Development

Different teams can work independently.

Example:

```
Team A

↓

Student Service

-------------------

Team B

↓

Payment Service

-------------------

Team C

↓

Notification Service
```

No dependency during development.

---

# Communication Between Microservices

Microservices need to communicate with each other.

Common communication methods:

- REST APIs
- HTTP
- Feign Client
- gRPC
- RabbitMQ
- Apache Kafka

Example:

```
Student Service

↓

REST API

↓

Notification Service

↓

Email Sent
```

---

# API Gateway

Instead of calling every service directly,

Client communicates with one API Gateway.

```
Client

↓

API Gateway

↓

Student Service

↓

Payment Service

↓

Notification Service
```

Benefits:

- Security
- Authentication
- Routing
- Load Balancing

---

# Service Discovery (Introduction)

Suppose there are:

```
10 Student Service Instances
```

How will another service know which instance to call?

Service Discovery solves this.

Popular Tool:

```
Netflix Eureka
```

We'll study this in Spring Cloud.

---

# Database in Microservices

Best Practice:

Each service should have its own database.

Example:

```
Student Service

↓

Student Database

-----------------

Payment Service

↓

Payment Database

-----------------

Notification Service

↓

Notification Database
```

Why?

Because services should remain independent.

---

# Advantages of Microservices

## Independent Deployment

Deploy one service without affecting others.

---

## Independent Scaling

Scale only the service receiving heavy traffic.

Example:

```
Payment Service

↓

High Traffic

↓

Scale Only Payment Service
```

---

## Fault Isolation

If one service crashes,

Others continue working.

---

## Faster Development

Different teams work simultaneously.

---

## Technology Flexibility

Example:

```
Student Service

↓

Java

-----------------

Analytics Service

↓

Python

-----------------

Notification Service

↓

Node.js
```

Each service can use a different technology.

---

# Disadvantages of Microservices

## Complex Architecture

Managing multiple services is difficult.

---

## Network Calls

Services communicate over the network.

Network latency increases.

---

## Difficult Debugging

One request may pass through multiple services.

Finding bugs becomes harder.

---

## Deployment Complexity

Many services require advanced DevOps tools.

---

## Data Consistency

Maintaining transactions across multiple databases is challenging.

---

# Monolithic vs Microservices

| Monolithic | Microservices |
|------------|---------------|
|Single Project|Multiple Projects|
|Single Deployment|Independent Deployment|
|Usually One Database|Database Per Service|
|Simple Architecture|Complex Architecture|
|Easy to Develop|Difficult Initially|
|Easy Debugging|Distributed Debugging|
|Hard to Scale Specific Module|Independent Scaling|
|Technology Locked|Technology Independent|

---

# Real-World Examples

## Monolithic Applications

- College Projects
- Library Management System
- Student Management System
- Hospital Management System (Small Scale)

---

## Microservices Applications

- Amazon
- Netflix
- Uber
- Swiggy
- Zomato
- Paytm
- Flipkart

Large companies prefer Microservices because millions of users access different modules simultaneously.

---

# When Should You Use Monolithic?

Use Monolithic when:

- Learning Spring Boot
- Building a College Project
- Working in a Small Team
- Developing an MVP (Minimum Viable Product)
- Building Small Business Applications

---

# When Should You Use Microservices?

Use Microservices when:

- Large Team
- Large User Base
- Independent Teams
- High Traffic
- Frequent Deployments
- Independent Scaling Required

---

# Which Architecture Should You Learn First?

Always start with:

```
Monolithic Architecture
```

Reason:

Microservices are built using Spring Boot.

Without understanding:

- Controllers
- Services
- Repository
- JPA
- REST APIs
- Spring Security

Learning Microservices becomes difficult.

---

# How Spring Boot Fits In

```
Spring Boot

↓

Build Monolithic Applications

↓

Understand REST APIs

↓

Learn Spring Cloud

↓

Build Microservices
```

Spring Boot is the foundation.

Spring Cloud is used to build Microservices.

---

# Common Mistakes

## Starting Directly with Microservices

Wrong approach.

First master Spring Boot.

---

## Using Microservices for Small Projects

Unnecessary complexity.

---

## Sharing One Database Between All Services

Avoid this.

Each service should ideally own its own database.

---

## Tight Coupling Between Services

Microservices should remain independent.

---

# Interview Questions

### What is Monolithic Architecture?

A software architecture where the entire application is built as a single deployable unit.

---

### What are Microservices?

Microservices are small, independent services that work together to build a complete application.

---

### Why were Microservices introduced?

To overcome the limitations of Monolithic Architecture such as difficult scaling, deployment, and maintenance.

---

### Which architecture is easier for beginners?

Monolithic Architecture.

---

### Can Microservices have separate databases?

Yes.

It is considered a best practice.

---

### How do Microservices communicate?

Using REST APIs, HTTP, Feign Client, gRPC, RabbitMQ, Kafka, etc.

---

### What is an API Gateway?

A single entry point for all client requests that routes them to the appropriate microservice.

---

### Which Spring project is used for Microservices?

Spring Cloud.

---

### Difference Between Monolithic and Microservices?

Monolithic:

- Single Project
- Single Deployment
- Easy Development

Microservices:

- Multiple Independent Projects
- Independent Deployment
- Better Scalability

---

# Summary

In this chapter, we learned:

- Software Architecture
- Monolithic Architecture
- Microservices Architecture
- Why Microservices were introduced
- API Gateway
- Service Discovery
- Database Strategy
- Advantages & Disadvantages
- Monolithic vs Microservices
- Real-world examples
- Interview questions

---

# Quick Revision

```
Monolithic

One Project

↓

One Deployment

↓

One Database

↓

Easy for Small Applications

--------------------------------

Microservices

Many Projects

↓

Independent Deployment

↓

Database Per Service

↓

Best for Large Applications
```

---

# Progress

- [x] Software Architecture
- [x] Monolithic Architecture
- [x] Microservices
- [x] API Gateway
- [x] Service Discovery