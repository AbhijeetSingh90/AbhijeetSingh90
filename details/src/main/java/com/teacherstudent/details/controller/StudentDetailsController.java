package com.teacherstudent.details.controller;

import com.teacherstudent.details.apireponse.ApiResponse;
import com.teacherstudent.details.dto.StudentDetailsDto;
import com.teacherstudent.details.serviceimpl.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentDetailsController {

    @Autowired
    public StudentDetailsService studentService;


    @GetMapping(path = "/get/studentDetails/{byId}")
    public ResponseEntity<ApiResponse<StudentDetailsDto>> getStudentDetailsById(@PathVariable("byId") long id) {
        return studentService.getStudentDetailsById(id);
    }

    @GetMapping(path = "/get/studentDetails/list")
    public ResponseEntity<ApiResponse<List<StudentDetailsDto>>> getStudentDetailsList() {
        return studentService.getStudentDetailsList();
    }

    @PostMapping(path = "/studentDetails/save")
    public ResponseEntity<ApiResponse<StudentDetailsDto>> saveStudentDetails(@RequestBody StudentDetailsDto studentDto) {
        return studentService.saveStudentDetails(studentDto);
    }

    @PutMapping(path = "/studentDetails/update")
    public ResponseEntity<ApiResponse<StudentDetailsDto>> updateStudentDetails(@RequestBody StudentDetailsDto studentDto) {
        return studentService.updateStudentDetails(studentDto);
    }

    @DeleteMapping(path = "/studentDetails/delete/{id}")
    public ResponseEntity deleteStudentDetails(@PathVariable("id") long id) {
        return studentService.delectById(id);
    }


}
