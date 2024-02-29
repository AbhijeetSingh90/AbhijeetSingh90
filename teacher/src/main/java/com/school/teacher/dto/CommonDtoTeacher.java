package com.school.teacher.dto;

import com.school.teacher.datamodel.Student;

import java.util.Date;
import java.util.List;

public record CommonDtoTeacher(long teacherId, String teacherName, String teacherGender , List<Student> studentDetails,
                               long teacherDetailsId, long teacherMobileNo, String teacherAge, Date teacherDOB,
                               Date entryDate, Date updatedDate) {

}
