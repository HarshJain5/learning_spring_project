package com.spring.first_spring_proj.repository;

import com.spring.first_spring_proj.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student ,Long> {
}
