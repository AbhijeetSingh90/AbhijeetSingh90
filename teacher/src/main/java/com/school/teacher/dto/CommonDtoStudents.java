package com.school.teacher.dto;

import com.school.teacher.datamodel.Student;

import java.util.Date;

public record CommonDtoStudents(long studentId, String studentName, String studentGender,long studentMobileNo,String studentAge
        ,Date studentDOB,boolean status) {

    public static Student dtoToDataModels(CommonDtoStudents studentDtoStudents){
        return new Student(studentDtoStudents.studentId(),studentDtoStudents.studentName(),studentDtoStudents.studentGender(),studentDtoStudents.status());
    }
}
