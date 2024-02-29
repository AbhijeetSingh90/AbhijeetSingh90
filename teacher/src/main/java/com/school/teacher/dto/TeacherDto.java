package com.school.teacher.dto;

import com.school.teacher.datamodel.Student;
import com.school.teacher.datamodel.Teacher;

import java.util.ArrayList;
import java.util.List;

public record TeacherDto (long teacherId, String teacherName, String teacherGender , List<Student> studentDetails){

    public static TeacherDto dataModelToDto(Teacher teacher){
        return new TeacherDto(teacher.getTeacherId(),teacher.getTeacherName(),teacher.getTeacherGender(),teacher.getStudentDetails());
    }

    public static List<TeacherDto> dataModelToDtoList(List<Teacher> teacherList){
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (Teacher teacher : teacherList){
            teacherDtos.add(dataModelToDto(teacher));
        }
        return teacherDtos;
    }

    public static Teacher dtoToDataModel(TeacherDto teacherDto){
        return new Teacher(teacherDto.teacherId(),teacherDto.teacherName(),teacherDto.teacherGender(),teacherDto.studentDetails());
    }

}
