package com.teacherstudent.details.dto;

import com.teacherstudent.details.datamodel.StudentDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public record StudentDetailsDto(long studentDetailsId, long studentMobileNo, String studentAge, Date studentDOB,
                                Timestamp entryDate, Timestamp updatedDate, long studentId) {

    public static StudentDetailsDto dataModelToDto(StudentDetails studentDetails){
        return new StudentDetailsDto(studentDetails.getStudentDetailsId(),studentDetails.getStudentMobileNo(),
                studentDetails.getStudentAge(),studentDetails.getStudentDOB(),studentDetails.getEntryDate(),
                studentDetails.getUpdatedDate(),studentDetails.getStudentId());
    }

    public static StudentDetails dtoToDataModel(StudentDetailsDto studentDto){
        return new StudentDetails(studentDto.studentDetailsId,studentDto.studentMobileNo,studentDto.studentAge,
                studentDto.studentDOB,studentDto.entryDate,studentDto.updatedDate,studentDto.studentId);
    }

    public static List<StudentDetailsDto> dataModelToDtoList(List<StudentDetails> studentList){
        List<StudentDetailsDto> studentDtos = new ArrayList<>();
        for (StudentDetails student : studentList){
            studentDtos.add(dataModelToDto(student));
        }
        return studentDtos;
    }
}