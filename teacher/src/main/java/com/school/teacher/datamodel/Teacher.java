package com.school.teacher.datamodel;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "teacher")
public class Teacher {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long teacherId;

    private String teacherName;

    private String teacherGender;

    @OneToMany
    private List<Student> studentDetails;


    public Teacher(long teacherId, String teacherName, String teacherGender, List<Student> studentDetails) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherGender = teacherGender;
        this.studentDetails = studentDetails;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }

    public List<Student> getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(List<Student> studentDetails) {
        this.studentDetails = studentDetails;
    }


}
