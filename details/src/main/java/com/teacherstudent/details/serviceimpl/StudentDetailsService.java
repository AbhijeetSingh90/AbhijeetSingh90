package com.teacherstudent.details.serviceimpl;

import com.teacherstudent.details.apireponse.ApiResponse;
import com.teacherstudent.details.datamodel.StudentDetails;
import com.teacherstudent.details.dto.StudentDetailsDto;
import com.teacherstudent.details.exception.ResourceNotFoundException;
import com.teacherstudent.details.repository.StudentDetailsRepository;
import com.teacherstudent.details.service.StudentDetailsImpl;
import com.teacherstudent.details.serviceimpl.ValidationAndOtherConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class StudentDetailsService implements StudentDetailsImpl {

    Logger logger = LoggerFactory.getLogger(StudentDetailsService.class);

    @Autowired
    public ValidationAndOtherConversion validationAndOtherConversion;

    @Autowired
    private StudentDetailsRepository studentRepository;

    @Override
    public ResponseEntity<ApiResponse<StudentDetailsDto>> getStudentDetailsById(long studentId) {
        StudentDetails student = studentRepository.findByStudentId(studentId);
        logger.info("Got Details" + student);
        if(student == null){
            return new ResponseEntity<ApiResponse<StudentDetailsDto>>(new ApiResponse<>("Not Found Data",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<StudentDetailsDto>>(new ApiResponse<>("Student Found",StudentDetailsDto.dataModelToDto(student)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<StudentDetailsDto>> updateStudentDetails(StudentDetailsDto studentDto) {
        if(studentDto == null) {
            return new ResponseEntity<ApiResponse<StudentDetailsDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        StudentDetails studentD = studentDto.dtoToDataModel(studentDto);
        logger.info("Got Details" + studentD);
        studentRepository.save(studentD);

        return new ResponseEntity<ApiResponse<StudentDetailsDto>>(new ApiResponse<>("Data Saved", StudentDetailsDto.dataModelToDto(studentD)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<List<StudentDetailsDto>>> getStudentDetailsList(){
        List<StudentDetails> studentList = studentRepository.findAll();
        logger.info("Got Details" + studentList);
        if(studentList.isEmpty()){
            return new ResponseEntity<ApiResponse<List<StudentDetailsDto>>>(new ApiResponse<>("No Data Found",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<List<StudentDetailsDto>>>(new ApiResponse<>("Recieved Student List",StudentDetailsDto.dataModelToDtoList(studentList)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<StudentDetailsDto>> saveStudentDetails(StudentDetailsDto studentDtoObj) {
        if(studentDtoObj == null) {
            return new ResponseEntity<ApiResponse<StudentDetailsDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        StudentDetails byStudentId = studentRepository.findByStudentId(studentDtoObj.studentId());
        StudentDetails student = StudentDetailsDto.dtoToDataModel(studentDtoObj);
        StudentDetails save ;
        if(byStudentId != null){
                byStudentId.setStudentMobileNo(student.getStudentMobileNo());
                byStudentId.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                byStudentId.getStudentDOB(student.getStudentDOB());
                int age = validationAndOtherConversion.calculateAge(studentDtoObj.studentDOB());
                String ages = String.valueOf(age);
                if(!(ages.equals(studentDtoObj.studentAge()))){
                    throw new ResourceNotFoundException("Details are Wrong ", "Age",studentDtoObj.studentAge());
                }
                byStudentId.setStudentAge(ages);
                save = studentRepository.save(byStudentId);
        }else {
            int age = validationAndOtherConversion.calculateAge(studentDtoObj.studentDOB());
            String ages = String.valueOf(age);
            if(!(ages.equals(studentDtoObj.studentAge()))){
                throw new ResourceNotFoundException("Details are Wrong ", "Age",studentDtoObj.studentAge());
            }
            student.setStudentAge(ages);
            student.setEntryDate(new Timestamp(System.currentTimeMillis()));
            student.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            save = studentRepository.save(student);
        }


//        logger.info("Got Details" + save);
        return new ResponseEntity<ApiResponse<StudentDetailsDto>>(new ApiResponse<>("Student Details Saved",StudentDetailsDto.dataModelToDto(save)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity delectById(long id) {
        Optional<StudentDetails> byId = studentRepository.findById(id);
        if(!(byId.isPresent())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Got Details" + byId);
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
