package com.spring.first_spring_proj.controllers;

import com.spring.first_spring_proj.entities.Student;
import com.spring.first_spring_proj.exceptions.NotFoundExceptions;
import com.spring.first_spring_proj.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/student")
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

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody @Valid Student student){
        try {
            return ResponseEntity.ok(this.studentService.addStudent(student));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/{sID}")
    public ResponseEntity<?> getStudentById(@PathVariable(name = "sID") long studentId){
        try {
            Student student = this.studentService.getStudentById(studentId);
//            return ResponseEntity.ok(student);
            return new ResponseEntity<>(student,HttpStatus.OK);   //same work as abobve line
        } catch (NotFoundExceptions e) {
            return new ResponseEntity<>(Map.of("message",e.getMessage()),HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(Map.of("error",e.getMessage()));
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable long studentId,@RequestBody Student student){
        try{
            if (studentId != student.getId()){
                return ResponseEntity.badRequest().body(Map.of("message","The Id in the path are not same"));
            }
            Student updatedStudent=this.studentService.updateStudentById(student);
            return ResponseEntity.ok(updatedStudent);
        } catch (NotFoundExceptions e) {
            return new ResponseEntity<>(Map.of("message",e.getMessage()),HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(Map.of("error",e.getMessage()));
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable long studentId){
        try{
            this.studentService.deleteStudentById(studentId);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(Map.of("error",e.getMessage()));
        }
    }
}
