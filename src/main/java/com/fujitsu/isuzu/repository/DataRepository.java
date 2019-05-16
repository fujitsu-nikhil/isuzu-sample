package com.fujitsu.isuzu.repository;

import com.fujitsu.isuzu.domain.Data;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Data entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

}
