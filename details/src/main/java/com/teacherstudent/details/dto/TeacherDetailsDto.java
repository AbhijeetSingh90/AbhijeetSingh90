package com.teacherstudent.details.dto;

import com.teacherstudent.details.datamodel.TeacherDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public record TeacherDetailsDto(long teacherDetailsId, long teacherMobileNo, String teacherAge, Date teacherDOB,
                                Date entryDate, Date updatedDate, long teacherId) {

    public static TeacherDetailsDto dataModelToDto(TeacherDetails teacherDetails){
        return new TeacherDetailsDto(teacherDetails.getTeacherDetailsId(),teacherDetails.getTeacherMobileNo(),
                teacherDetails.getTeacherAge(),teacherDetails.getTeacherDOB(),teacherDetails.getEntryDate(),
                teacherDetails.getUpdatedDate(),teacherDetails.getTeacherId());
    }

    public static TeacherDetails dtoToDataModel(TeacherDetailsDto teacherDetailsDto){
        return new TeacherDetails(teacherDetailsDto.teacherDetailsId,teacherDetailsDto.teacherMobileNo,teacherDetailsDto.teacherAge,
                teacherDetailsDto.teacherDOB,teacherDetailsDto.entryDate,teacherDetailsDto.updatedDate,teacherDetailsDto.teacherId);
    }

    public static List<TeacherDetailsDto> dataModelToDtoList(List<TeacherDetails> teacherDetailsList){
        List<TeacherDetailsDto> teacherDetailsDtos = new ArrayList<>();
        for (TeacherDetails teacherDetails : teacherDetailsList){
           teacherDetailsDtos.add(dataModelToDto(teacherDetails));
        }
        return teacherDetailsDtos;
    }

}
