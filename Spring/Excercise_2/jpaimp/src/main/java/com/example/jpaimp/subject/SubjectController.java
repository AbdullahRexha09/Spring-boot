package com.example.jpaimp.subject;

import com.example.jpaimp.student.Student;
import com.example.jpaimp.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subject")
public class SubjectController {
    @Autowired
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public SubjectController(SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }
    @GetMapping
    List<Subject> getSubjects(){ return subjectRepository.findAll();}
    @PostMapping
    public void createSubject(@RequestBody Subject subject){
        subjectRepository.save(subject);
    }
    @PutMapping("/{subjectId}/students/{studentId}")
    public Subject enrollStudentToSubject(@PathVariable Long subjectId, @PathVariable Long studentId){
        Subject subject = subjectRepository.getById(subjectId);
        Student student = studentRepository.getById(studentId);
        subject.enrollStudent(student);
        return subjectRepository.save(subject);
    }
}
