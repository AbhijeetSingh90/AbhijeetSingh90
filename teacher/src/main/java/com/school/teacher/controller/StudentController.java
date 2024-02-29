package com.school.teacher.controller;

import com.school.teacher.apiresponse.ApiResponse;
import com.school.teacher.dto.CommonDtoStudents;
import com.school.teacher.dto.StudentDto;
import com.school.teacher.serviceImpl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    public StudentService studentService;

    @GetMapping(path = "/get/student/{byId}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(@PathVariable("byId") long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping(path = "/get/student/list")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getStudentList() {
        return studentService.getStudentList();
    }

    @PostMapping(path = "/student/save")
    public ResponseEntity<ApiResponse<StudentDto>> saveStudent(@RequestBody CommonDtoStudents studentDto) {
        return studentService.saveStudent(studentDto);
    }

    @PutMapping(path = "/student/update")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(@RequestBody CommonDtoStudents studentDto) {
        return studentService.updateStudent(studentDto);
    }

    @DeleteMapping(path = "/student/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable("id") long id) {
        return studentService.delectById(id);
    }

}
