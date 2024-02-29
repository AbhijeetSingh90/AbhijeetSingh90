package com.teacherstudent.details.repository;

import com.teacherstudent.details.datamodel.TeacherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDetailsRepository extends JpaRepository<TeacherDetails,Long> {

}
