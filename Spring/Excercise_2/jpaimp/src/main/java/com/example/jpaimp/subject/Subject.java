package com.example.jpaimp.subject;

import com.example.jpaimp.student.Student;
import com.example.jpaimp.teacher.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Subject")

public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "students_enrolled",
            joinColumns = @JoinColumn(
                    name = "subject_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id"
            )
    )
    private Set<Student> students;
    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;


    public void enrollStudent(Student student) {
        students.add(student);
    }
}
