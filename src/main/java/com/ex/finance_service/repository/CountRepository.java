package com.ex.finance_service.repository;

import com.ex.finance_service.entity.Count;
import com.ex.finance_service.enums.CountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountRepository extends JpaRepository<Count, UUID> {

    @Query(value = "select c from Count c where c.routeEventId = :routeEventId order by c.createDt limit 1")
    Count findByRouteEventId(UUID routeEventId);

}
