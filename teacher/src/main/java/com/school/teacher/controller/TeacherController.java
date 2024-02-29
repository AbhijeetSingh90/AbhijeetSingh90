package com.school.teacher.controller;

import com.school.teacher.apiresponse.ApiResponse;
import com.school.teacher.dto.TeacherDto;
import com.school.teacher.serviceImpl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    public TeacherService teacherService;


    @GetMapping(path = "/get/teacher/{byId}")
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(@PathVariable("byId") long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping(path = "/get/teacher/list")
    public ResponseEntity<ApiResponse<List<TeacherDto>>> getTeacherList() {
        return teacherService.getTeacherList();
    }

    @PostMapping(path = "/teacher/save")
    public ResponseEntity<ApiResponse<TeacherDto>> saveTeacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.saveTeacher(teacherDto);
    }

    @PutMapping(path = "/teacher/update")
    public ResponseEntity<ApiResponse<TeacherDto>> updateTeacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.updateTeacher(teacherDto);
    }

    @DeleteMapping(path = "/teacher/delete/{id}")
    public ResponseEntity deleteTeacher(@PathVariable("id") long id) {
        return teacherService.delectById(id);
    }


}
