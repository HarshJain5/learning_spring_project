package com.spring.first_spring_proj.controllers;

import com.spring.first_spring_proj.entities.Student;
import com.spring.first_spring_proj.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/")
    public String hello() {
        return "Server Started" ;
    }

    @PostMapping("/student/add")
    public Student addStudent(@RequestBody Student student){
        return this.studentService.addStudent(student);
    }

    @GetMapping("/student/{sID}")
    public Student getStudentById(@PathVariable(name = "sID") long studentId){
        return this.studentService.getStudentById(studentId);
    }

    @PutMapping("/student/{studentId}")
    public Student updateStudent(@PathVariable long studentId,@RequestBody Student student){
        if (studentId != student.getId()){

        }
        return this.studentService.updateStudentById(student);
    }

    @DeleteMapping("/student/{studentId}")
    public void deleteStudentById(@PathVariable long studentId){
        this.studentService.deleteStudentById(studentId);
    }
}
