package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }
    @PostMapping
    public Student addStudent(@RequestBody Student student){
        System.out.println("hello");
        return studentService.addStudent(student);
    }
    @DeleteMapping("{studentId}")
    public void delete(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
}
