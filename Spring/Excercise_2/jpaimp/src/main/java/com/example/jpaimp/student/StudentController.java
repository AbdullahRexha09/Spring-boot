package com.example.jpaimp.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
@Autowired
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @GetMapping
    List<Student> getStudents(){ return studentRepository.findAll();}
    @PostMapping
    public void createStudent(@RequestBody Student student){
        studentRepository.save(student);
    }
}
