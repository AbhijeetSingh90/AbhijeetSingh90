package com.school.teacher.service;

import com.school.teacher.apiresponse.ApiResponse;
import com.school.teacher.dto.CommonDtoStudents;
import com.school.teacher.dto.StudentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentServiceImpl {

    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(long studentId);

    public ResponseEntity<ApiResponse<StudentDto>> saveStudent(CommonDtoStudents studentDto);

    public ResponseEntity<ApiResponse<List<StudentDto>>> getStudentList();

    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(CommonDtoStudents commonDtoStudents);

    public ResponseEntity<ApiResponse<StudentDto>> delectById(long Id);

}
