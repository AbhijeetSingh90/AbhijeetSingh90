package com.teacherstudent.details.datamodel;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "teacherdetails")
public class TeacherDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long teacherDetailsId;

    public long teacherMobileNo;

    public String teacherAge;

    public Date teacherDOB;

    public Date entryDate;

    public Date updatedDate;

    public long teacherId;

    public TeacherDetails(long teacherDetailsId, long teacherMobileNo, String teacherAge, Date teacherDOB, Date entryDate, Date updatedDate, long teacherId) {
        this.teacherDetailsId = teacherDetailsId;
        this.teacherMobileNo = teacherMobileNo;
        this.teacherAge = teacherAge;
        this.teacherDOB = teacherDOB;
        this.entryDate = entryDate;
        this.updatedDate = updatedDate;
        this.teacherId = teacherId;
    }

    public long getTeacherDetailsId() {
        return teacherDetailsId;
    }

    public void setTeacherDetailsId(long teacherDetailsId) {
        this.teacherDetailsId = teacherDetailsId;
    }

    public long getTeacherMobileNo() {
        return teacherMobileNo;
    }

    public void setTeacherMobileNo(long teacherMobileNo) {
        this.teacherMobileNo = teacherMobileNo;
    }

    public String getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(String teacherAge) {
        this.teacherAge = teacherAge;
    }

    public Date getTeacherDOB() {
        return teacherDOB;
    }

    public void setTeacherDOB(Date teacherDOB) {
        this.teacherDOB = teacherDOB;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}
