package com.teacherstudent.details.service;

import com.teacherstudent.details.apireponse.ApiResponse;
import com.teacherstudent.details.dto.TeacherDetailsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeacherDetailsImpl {

    public ResponseEntity<ApiResponse<TeacherDetailsDto>> getTeacherDetailsById(long teacherId);

    public ResponseEntity<ApiResponse<TeacherDetailsDto>> saveTeacherDetails(TeacherDetailsDto teacherDetailsDto);

    public ResponseEntity<ApiResponse<List<TeacherDetailsDto>>> getTeacherDetailsList();

    public ResponseEntity<ApiResponse<TeacherDetailsDto>> updateTeacherDetails(TeacherDetailsDto teacherDetailsDto);

    public ResponseEntity<ApiResponse<TeacherDetailsDto>> delectById(long Id);

}
