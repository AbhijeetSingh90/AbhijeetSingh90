package com.teacherstudent.details.controller;

import com.teacherstudent.details.apireponse.ApiResponse;
import com.teacherstudent.details.dto.TeacherDetailsDto;
import com.teacherstudent.details.serviceimpl.TeacherDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherDetailsController {

    @Autowired
    public TeacherDetailsService teacherService;

    @GetMapping(path = "/get/teacherDetails/{byId}")
    public ResponseEntity<ApiResponse<TeacherDetailsDto>> getTeacherDetailsById(@PathVariable("byId") long id) {
        return teacherService.getTeacherDetailsById(id);
    }

    @GetMapping(path = "/get/teacherDetails/list")
    public ResponseEntity<ApiResponse<List<TeacherDetailsDto>>> getTeacherDetailsList() {
        return teacherService.getTeacherDetailsList();
    }

    @PostMapping(path = "/teacherDetails/save")
    public ResponseEntity<ApiResponse<TeacherDetailsDto>> saveTeacherDetails(@RequestBody TeacherDetailsDto teacherDto) {
        return teacherService.saveTeacherDetails(teacherDto);
    }

    @PutMapping(path = "/teacherDetails/update")
    public ResponseEntity<ApiResponse<TeacherDetailsDto>> updateTeacherDetails(@RequestBody TeacherDetailsDto teacherDto) {
        return teacherService.updateTeacherDetails(teacherDto);
    }

    @DeleteMapping(path = "/teacherDetails/delete/{id}")
    public ResponseEntity deleteTeacherDetails(@PathVariable("id") long id) {
        return teacherService.delectById(id);
    }


}
