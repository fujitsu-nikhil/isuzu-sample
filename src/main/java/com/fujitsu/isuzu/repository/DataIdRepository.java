package com.fujitsu.isuzu.repository;

import com.fujitsu.isuzu.domain.DataId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataIdRepository extends JpaRepository<DataId, Long> {

}
