package com.hsbc.incident.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hsbc.incident.domain.model.entity.Incident;
import com.hsbc.incident.domain.repository.IncidentRepository;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.constant.ErrorCode;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepo;

    @InjectMocks
    private IncidentService incidentService;

    // Create new incident
    @Test
    public void shouldCreateIncidentWhenIncidentIsNewGivenValidIncident() {
        Incident incident = new Incident();
        incident.setId(null);
        when(incidentRepo.persist(incident)).thenReturn(incident);

        Incident result = incidentService.createOrUpdateIncident(incident);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(incident);
        verify(incidentRepo).persist(incident);
    }

    // Update existing incident
    @Test
    public void shouldUpdateIncidentWhenIncidentExistsGivenValidIncident() {
        Incident incident = new Incident();
        incident.setId(1L);
        when(incidentRepo.save(incident)).thenReturn(incident);

        Incident result = incidentService.createOrUpdateIncident(incident);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(incident);
        verify(incidentRepo).save(incident);
    }

    // Delete existed incident
    @Test
    public void shouldDeleteIncidentWhenDeleteExistedIncidentGivenInvalidId() {
        String incidentId = "999";
        lenient().when(incidentRepo.findById(999L))
            .thenReturn(Optional.of(new Incident().setId(999L)));

        incidentService.deleteIncident(incidentId);

        verify(incidentRepo).deleteById(999L);
    }

    // Delete non-existed incident
    @Test
    public void shouldNotThrowExceptionWhenDeleteNonExistentIncidentGivenInvalidId() {
        String incidentId = "999";
        lenient().when(incidentRepo.findById(999L)).thenReturn(Optional.empty());

        incidentService.deleteIncident(incidentId);

        verify(incidentRepo).deleteById(999L);
    }

    // Get existed incident
    @Test
    public void shouldReturnIncidentWhenGetExistentIncidentGivenInvalidId() {
        String incidentId = "999";
        when(incidentRepo.findById(999L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            incidentService.getIncident(incidentId);
        });
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INCIDENT_NOT_FOUND);
    }

    // Get non-existed incident with valid id
    @Test
    public void shouldThrowBusinessExceptionWhenGetIncidentGivenNonExistentId() {
        String incidentId = "999";
        when(incidentRepo.findById(999L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            incidentService.getIncident(incidentId);
        });
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INCIDENT_NOT_FOUND);
    }

    // Get non-existed incident with invalid id
    @Test
    public void shouldThrowBusinessExceptionWhenGetIncidentGivenInvalidExistentId() {
        String incidentId = "abc";

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            incidentService.getIncident(incidentId);
        });
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INCIDENT_NOT_FOUND);
    }

    // List all incidents
    @Test
    public void shouldListAllIncidents_whenNoIncidentsExist_givenEmptyList() {
        when(incidentRepo.findAll()).thenReturn(Collections.emptyList());

        assertThat(incidentService.listAllIncidents()).isEqualTo(Collections.emptyList());
        verify(incidentRepo).findAll();
    }
}
