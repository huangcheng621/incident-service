package com.hsbc.incident.domain.service;

import com.hsbc.incident.domain.model.entity.Incident;
import com.hsbc.incident.domain.repository.IncidentRepository;
import com.hsbc.incident.shared.constant.ErrorCode;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.utils.NumericUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class IncidentService {

    private final IncidentRepository incidentRepo;

    public Incident createOrUpdateIncident(Incident incident) {
        if (incident.getId() != null) {
            log.info("Update incident: {}", incident);
            return incidentRepo.save(incident);
        } else {
            log.info("Creating incident: {}", incident);
            return incidentRepo.persist(incident);
        }
    }

    // If incident is not found, no exception will be thrown for the sake of idempotence.
    public void deleteIncident(String incidentId) {
        log.info("Deleting incident with id: {}", incidentId);
        NumericUtils.parseLong(incidentId)
            .ifPresent(incidentRepo::deleteById);
    }

    public Incident getIncident(String incidentId) {
        return NumericUtils.parseLong(incidentId)
            .flatMap(incidentRepo::findById)
            .orElseThrow(() -> BusinessException.notFound(ErrorCode.INCIDENT_NOT_FOUND,
                String.valueOf(incidentId)));
    }

    public List<Incident> listAllIncidents() {
        return incidentRepo.findAll();
    }
}
