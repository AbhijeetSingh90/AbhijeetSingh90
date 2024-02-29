package com.school.teacher.serviceImpl;

import com.school.teacher.apiresponse.ApiResponse;
import com.school.teacher.datamodel.Teacher;
import com.school.teacher.dto.TeacherDto;
import com.school.teacher.repository.TeacherRepository;
import com.school.teacher.service.TeacherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeacherService implements TeacherServiceImpl {


    Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        if(teacher == null){
            return new ResponseEntity<ApiResponse<TeacherDto>>(new ApiResponse<>("Not Found Data",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<TeacherDto>>(new ApiResponse<>("Student Found",TeacherDto.dataModelToDto(teacher)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<TeacherDto>> updateTeacher(TeacherDto teacherDto) {
        if(teacherDto == null) {
            return new ResponseEntity<ApiResponse<TeacherDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        Teacher teacherD =TeacherDto.dtoToDataModel(teacherDto);
        teacherRepository.save(teacherD);

        return new ResponseEntity<ApiResponse<TeacherDto>>(new ApiResponse<>("Data Saved", TeacherDto.dataModelToDto(teacherD)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<List<TeacherDto>>> getTeacherList(){
        List<Teacher> teacherList= teacherRepository.findAll();
        if(teacherList.isEmpty()){
            return new ResponseEntity<ApiResponse<List<TeacherDto>>>(new ApiResponse<>("No Data Found",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<List<TeacherDto>>>(new ApiResponse<>("Recieved Student List",TeacherDto.dataModelToDtoList(teacherList)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<TeacherDto>> saveTeacher(TeacherDto teacherDtoObj) {
         if(teacherDtoObj == null) {
            return new ResponseEntity<ApiResponse<TeacherDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        Teacher teacher = TeacherDto.dtoToDataModel(teacherDtoObj);
        Teacher save = teacherRepository.save(teacher);
        return new ResponseEntity<ApiResponse<TeacherDto>>(new ApiResponse<>("Student Details Saved",TeacherDto.dataModelToDto(save)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity delectById(long id) {
        Optional<Teacher> byId = teacherRepository.findById(id);
        if(!(byId.isPresent())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        teacherRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
