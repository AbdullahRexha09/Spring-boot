package com.example.jpaimp.student;
import com.example.jpaimp.subject.Subject;
import com.example.jpaimp.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;

}
