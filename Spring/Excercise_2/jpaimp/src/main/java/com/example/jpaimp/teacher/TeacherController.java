package com.example.jpaimp.teacher;

import com.example.jpaimp.subject.Subject;
import com.example.jpaimp.subject.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
@Autowired
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    @GetMapping
    List<Teacher> getTeachers(){ return teacherRepository.findAll();}

    @PostMapping
    public void createTeacher(@RequestBody Teacher teacher){
        teacherRepository.save(teacher);
    }

}
