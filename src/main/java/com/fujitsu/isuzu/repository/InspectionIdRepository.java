package com.fujitsu.isuzu.repository;

import com.fujitsu.isuzu.domain.InspectionId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InspectionId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionIdRepository extends JpaRepository<InspectionId, Long> {

}
