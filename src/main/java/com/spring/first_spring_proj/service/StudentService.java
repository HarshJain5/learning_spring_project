package com.spring.first_spring_proj.service;

import com.spring.first_spring_proj.entities.Student;
import com.spring.first_spring_proj.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student){
        Student addedStudent=this.studentRepository.save(student);
        return addedStudent;
    }
    public Student getStudentById(long studentId){
        Optional<Student> optionalValue = this.studentRepository.findById(studentId);
        return optionalValue.orElse(null);
    }
    public Student updateStudentById(Student student){
        Student existingStudent = this.getStudentById(student.getId());
        if (existingStudent==null){
            return null;
        }
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        return this.studentRepository.save(existingStudent);
    }
    public void deleteStudentById(long studentId){
        this.studentRepository.deleteById(studentId);
    }
}
