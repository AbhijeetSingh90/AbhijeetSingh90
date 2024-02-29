package com.school.teacher.repository;

import com.school.teacher.datastate.DataState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataStateRepository extends JpaRepository<DataState, Long> {

        DataState findByDataTypeAndDataReferenceAndContext(String type, String id, String context);

}

