package com.fujitsu.isuzu.repository;

import com.fujitsu.isuzu.domain.InspectionResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InspectionResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionResultRepository extends JpaRepository<InspectionResult, Long> {

}
