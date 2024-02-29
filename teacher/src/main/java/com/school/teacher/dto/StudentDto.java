package com.school.teacher.dto;

import com.school.teacher.datamodel.Student;

import java.util.ArrayList;
import java.util.List;

public record StudentDto(long studentId, String studentName, String studentGender ,boolean status) {

    public static StudentDto dataModelToDto(Student student){
        return new StudentDto(student .getStudentId(),student.getStudentName(),student.getStudentGender(),student.isStatus());
    }

    public static Student dtoToDataModel(StudentDto studentDto){
        return new Student(studentDto.studentId(),studentDto.studentName(),studentDto.studentGender(),studentDto.status() );
    }

    public static List<StudentDto> dataModelToDtoList(List<Student> studentList){
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student student : studentList){
            studentDtos.add(dataModelToDto(student));
        }
        return studentDtos;
    }
}

