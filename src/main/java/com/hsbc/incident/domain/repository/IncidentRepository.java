package com.hsbc.incident.domain.repository;

import com.hsbc.incident.domain.model.entity.Incident;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long>,
    BaseJpaRepository<Incident, Long> {

}
