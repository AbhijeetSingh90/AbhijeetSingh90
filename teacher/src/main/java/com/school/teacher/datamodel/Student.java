package com.school.teacher.datamodel;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "student")
public class Student {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long studentId;

    private String studentName;

    private String studentGender;

    private boolean status;

    public Student(long studentId, String studentName, String studentGender, boolean status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentGender = studentGender;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public Student() {
    }
}
