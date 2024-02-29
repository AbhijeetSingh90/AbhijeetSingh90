package com.teacherstudent.details.datamodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.ScopeMetadata;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "studentdetails" ,indexes = { @Index(columnList = "student_id") ,@Index(columnList = "student_dob") })
public class StudentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long studentDetailsId;

    public long studentMobileNo;

    public String studentAge;

    @Column(name = "student_dob")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public Date studentDOB;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    public Timestamp entryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    public Timestamp updatedDate;

    @Column(unique=true ,name = "student_id")
    public long studentId;

    public StudentDetails(long studentDetailsId, long studentMobileNo, String studentAge, Date studentDOB, Timestamp entryDate, Timestamp updatedDate, long studentId) {
        this.studentDetailsId = studentDetailsId;
        this.studentMobileNo = studentMobileNo;
        this.studentAge = studentAge;
        this.studentDOB = studentDOB;
        this.entryDate = entryDate;
        this.updatedDate = updatedDate;
        this.studentId = studentId;
    }

    public StudentDetails() {
        // Initialize default values or leave it empty
    }

    public StudentDetails(Long studentId) {
        this.studentId = studentId;
        // Initialize other fields if needed
    }

    public long getStudentDetailsId() {
        return studentDetailsId;
    }

    public void setStudentDetailsId(long studentDetailsId) {
        this.studentDetailsId = studentDetailsId;
    }

    public long getStudentMobileNo() {
        return studentMobileNo;
    }

    public void setStudentMobileNo(long studentMobileNo) {
        this.studentMobileNo = studentMobileNo;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }

    public Date getStudentDOB() {
        return studentDOB;
    }

    public void setStudentDOB(Date studentDOB) {
        this.studentDOB = studentDOB;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void getStudentDOB(Object studentDOB) {
    }
}
