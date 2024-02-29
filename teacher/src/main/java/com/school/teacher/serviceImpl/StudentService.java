package com.school.teacher.serviceImpl;

import com.school.teacher.apiresponse.ApiResponse;
import com.school.teacher.datamodel.Student;
import com.school.teacher.datastate.DataNode;
import com.school.teacher.dto.CommonDtoStudents;
import com.school.teacher.dto.StudentDto;
import com.school.teacher.exception.ResourceNotFoundException;
import com.school.teacher.repository.StudentRepository;
import com.school.teacher.service.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class StudentService implements StudentServiceImpl {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public DataStateService dataStateService;

    @Override
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(long studentId) {
        Student student = studentRepository.findById(studentId).get();
        logger.info("Got Details" + student);
        if(student == null){
            return new ResponseEntity<ApiResponse<StudentDto>>(new ApiResponse<>("Not Found Data",null),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<StudentDto>>(new ApiResponse<>("Student Found",StudentDto.dataModelToDto(student)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(CommonDtoStudents studentDto) {
        if(studentDto == null) {
            return new ResponseEntity<ApiResponse<StudentDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        Optional<Student> byId = studentRepository.findById(studentDto.studentId());
        if(!(byId.isPresent())){
            throw new ResourceNotFoundException(String.valueOf(studentDto.studentId()),studentDto.studentName(),studentDto.studentGender());
        }
        Student studentD = studentDto.dtoToDataModels(studentDto);
        CommonDtoStudents commonDtoStudents = saveStudentDetails(studentDto);
        studentD.setStatus(true);
        Student save = studentRepository.save(studentD);
        if (save.isStatus()) {
            dataStateService.setSectionStatus(DataNode.Status.COMPLETED, "StudentDetails", String.valueOf(save.getStudentId()), String.valueOf(save.getStudentId()) , "Student");
        }
        logger.info("Got Details" + studentD);
        return new ResponseEntity<ApiResponse<StudentDto>>(new ApiResponse<>("Data Saved", StudentDto.dataModelToDto(studentD)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<List<StudentDto>>> getStudentList(){
        List<Student> studentList = studentRepository.findAll();
        logger.info("Got Details" + studentList);
        if(studentList.isEmpty()){
            return new ResponseEntity<ApiResponse<List<StudentDto>>>(new ApiResponse<>("No Data Found",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse<List<StudentDto>>>(new ApiResponse<>("Recieved Student List",StudentDto.dataModelToDtoList(studentList)),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApiResponse<StudentDto>> saveStudent(CommonDtoStudents studentDtoObj) {
        if(studentDtoObj == null) {
            return new ResponseEntity<ApiResponse<StudentDto>>(new ApiResponse<>( "Not Found Data", null), HttpStatus.NOT_FOUND);
        }
        Student student = CommonDtoStudents.dtoToDataModels(studentDtoObj);
        Student studentSavedData = studentRepository.save(student);
        logger.info("Got Details" + studentSavedData);
        dataStateService.setSectionStatus(DataNode.Status.IN_PROGRESS, "StudentDetails", String.valueOf(studentSavedData.getStudentId()), String.valueOf(studentSavedData.getStudentId()), "Student");
        return new ResponseEntity<ApiResponse<StudentDto>>(new ApiResponse<>("Student Details Saved",StudentDto.dataModelToDto(studentSavedData)),HttpStatus.FOUND);
    }

    public CommonDtoStudents saveStudentDetails(CommonDtoStudents commonDtoStudents){
        String currentUrl = "http://localhost:8089/studentDetails/save" ;
        HttpEntity<CommonDtoStudents> request = new HttpEntity<CommonDtoStudents>(commonDtoStudents);
        ResponseEntity<CommonDtoStudents> response = restTemplate.postForEntity(currentUrl,request, CommonDtoStudents.class, CommonDtoStudents.class);
        return response.getBody();
    }

    @Override
    public ResponseEntity delectById(long id) {
        Optional<Student> byId = studentRepository.findById(id);
        if(!(byId.isPresent())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Got Details" + byId);
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
