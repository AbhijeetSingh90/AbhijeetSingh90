package com.school.teacher.service;

import com.school.teacher.apiresponse.ApiResponse;
import com.school.teacher.datamodel.Teacher;
import com.school.teacher.dto.StudentDto;
import com.school.teacher.dto.TeacherDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeacherServiceImpl {

    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(long teacherId);

    public ResponseEntity<ApiResponse<TeacherDto>> saveTeacher(TeacherDto teacherDto);

    public ResponseEntity<ApiResponse<List<TeacherDto>>> getTeacherList();

    public ResponseEntity<ApiResponse<TeacherDto>> updateTeacher(TeacherDto teacherDto);

    public ResponseEntity<ApiResponse<TeacherDto>> delectById(long Id);


}
