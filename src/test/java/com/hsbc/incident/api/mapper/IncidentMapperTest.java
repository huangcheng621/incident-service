package com.hsbc.incident.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.hsbc.incident.api.request.CreateIncidentRequest;
import com.hsbc.incident.api.request.UpdateIncidentRequest;
import com.hsbc.incident.domain.model.entity.Incident;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class IncidentMapperTest {

    private final IncidentMapper mapper = Mappers.getMapper(IncidentMapper.class);

    @Test
    public void shouldReturnIncidentWhenMapCreateRequestToModel() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        request.setName("Incident Title")
            .setDescription("Incident Description")
            .setCategory("Category")
            .setSeverity("Severity")
            .setStatus("Status");

        Incident incident = mapper.createRequestToModel(request);

        assertThat(incident.getName()).isEqualTo(request.getName());
        assertThat(incident.getDescription()).isEqualTo(request.getDescription());
        assertThat(incident.getSeverity()).isEqualTo(request.getSeverity());
        assertThat(incident.getStatus()).isEqualTo(request.getStatus());
        assertThat(incident.getCategory()).isEqualTo(request.getCategory());
    }

    @Test
    public void shouldUpdateIncidentWhenMapUpdateRequestToModel() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setDescription("Incident Description");

        Incident incident = new Incident()
            .setId(1L).setDescription("Original Incident Description");

        mapper.updateModelFromRequest(request, incident);

        assertThat(incident.getDescription()).isEqualTo(request.getDescription());
    }
}