package com.teacherstudent.details.repository;

import com.teacherstudent.details.datamodel.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDetailsRepository extends JpaRepository<StudentDetails,Long> {

//    public StudentDetails findByStudentId(long studentId);

    @Query("select c from StudentDetails c where c.studentId=:studentId")
    StudentDetails findByStudentId(@Param("studentId") long studentId);

}
