package com.teacherstudent.details.service;

import com.teacherstudent.details.apireponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import com.teacherstudent.details.dto.StudentDetailsDto;

import java.util.List;

public interface StudentDetailsImpl {

    public ResponseEntity<ApiResponse<StudentDetailsDto>> getStudentDetailsById(long studentDetailsId);

    public ResponseEntity<ApiResponse<StudentDetailsDto>> saveStudentDetails(StudentDetailsDto studentDetailsDto);

    public ResponseEntity<ApiResponse<List<StudentDetailsDto>>> getStudentDetailsList();

    public ResponseEntity<ApiResponse<StudentDetailsDto>> updateStudentDetails(StudentDetailsDto studentDetailsDto);

    public ResponseEntity<ApiResponse<StudentDetailsDto>> delectById(long Id);

}
