package com.teacherstudent.details.serviceimpl;

import com.teacherstudent.details.apireponse.ApiResponse;
import com.teacherstudent.details.datamodel.TeacherDetails;
import com.teacherstudent.details.dto.TeacherDetailsDto;
import com.teacherstudent.details.repository.TeacherDetailsRepository;
import com.teacherstudent.details.service.TeacherDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeacherDetailsService implements TeacherDetailsImpl {


    Logger logger = LoggerFactory.getLogger(TeacherDetailsService.class);

    @Autowired
    private TeacherDetailsRepository teacherRepository;

    @Override
    public ResponseEntity<ApiResponse<TeacherDetailsDto>> getTeacherDetailsById(long teacherId) {
        TeacherDetails teacher = teacherRepository.findById(teacherId).get();
        if(teacher == null){
            return new ResponseEntity<ApiResponse<TeacherDetailsDto>>(new ApiResponse<>("Not Found Data",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<TeacherDetailsDto>>(new ApiResponse<>("Student Found",TeacherDetailsDto.dataModelToDto(teacher)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<TeacherDetailsDto>> updateTeacherDetails(TeacherDetailsDto teacherDto) {
        if(teacherDto == null) {
            return new ResponseEntity<ApiResponse<TeacherDetailsDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        TeacherDetails teacherD = TeacherDetailsDto.dtoToDataModel(teacherDto);
        teacherRepository.save(teacherD);
        return new ResponseEntity<ApiResponse<TeacherDetailsDto>>(new ApiResponse<>("Data Saved", TeacherDetailsDto.dataModelToDto(teacherD)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<List<TeacherDetailsDto>>> getTeacherDetailsList(){
        List<TeacherDetails> teacherList= teacherRepository.findAll();
        if(teacherList.isEmpty()){
            return new ResponseEntity<ApiResponse<List<TeacherDetailsDto>>>(new ApiResponse<>("No Data Found",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<List<TeacherDetailsDto>>>(new ApiResponse<>("Recieved Student List",TeacherDetailsDto.dataModelToDtoList(teacherList)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<TeacherDetailsDto>> saveTeacherDetails(TeacherDetailsDto teacherDtoObj) {
        if(teacherDtoObj == null) {
            return new ResponseEntity<ApiResponse<TeacherDetailsDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        TeacherDetails teacher = TeacherDetailsDto.dtoToDataModel(teacherDtoObj);
        TeacherDetails save = teacherRepository.save(teacher);
        return new ResponseEntity<ApiResponse<TeacherDetailsDto>>(new ApiResponse<>("Student Details Saved",TeacherDetailsDto.dataModelToDto(save)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity delectById(long id) {
        Optional<TeacherDetails> byId = teacherRepository.findById(id);
        if(!(byId.isPresent())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        teacherRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
