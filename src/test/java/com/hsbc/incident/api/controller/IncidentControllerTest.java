package com.hsbc.incident.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hsbc.incident.api.mapper.IncidentMapper;
import com.hsbc.incident.api.request.CreateIncidentRequest;
import com.hsbc.incident.api.request.UpdateIncidentRequest;
import com.hsbc.incident.config.JwtAuthenticationFilter;
import com.hsbc.incident.domain.model.entity.Incident;
import com.hsbc.incident.domain.service.IncidentService;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.constant.ErrorCode;
import com.hsbc.incident.shared.utils.JsonUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = IncidentController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthenticationFilter.class}))
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentMapper incidentMapper;

    @MockBean
    private IncidentService incidentService;

    // Test successful creation of incident
    @Test
    public void shouldReturnCreatedWhenCreateIncidentGivenValidRequest() throws Exception {
        CreateIncidentRequest request = new CreateIncidentRequest()
            .setName("Sample incident")
            .setDescription("Sample incident")
            .setCategory("Category")
            .setSeverity("Low")
            .setStatus("New");
        Incident incident = new Incident()
            .setName("Sample incident")
            .setDescription("Sample incident")
            .setCategory("Category")
            .setSeverity("Low")
            .setStatus("New");
        when(incidentMapper.createRequestToModel(request)).thenReturn(incident);

        when(incidentService.createOrUpdateIncident(any())).thenReturn(incident);

        mockMvc.perform(post("/v1/incidents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(incident.getId()))
            .andExpect(jsonPath("$.description").value(incident.getDescription()))
            .andExpect(jsonPath("$.category").value(incident.getCategory()))
            .andExpect(jsonPath("$.severity").value(incident.getSeverity()))
            .andExpect(jsonPath("$.status").value(incident.getStatus()));
    }

    // Test creation of incident with bad request
    @Test
    public void shouldReturn400WhenCreateIncidentGivenInvalidRequest() throws Exception {
        CreateIncidentRequest request = new CreateIncidentRequest()
            .setName(null) // mandatory field
            .setDescription("Sample incident")
            .setCategory("Category")
            .setSeverity("Low")
            .setStatus("New");
        Incident incident = new Incident()
            .setName("Sample incident")
            .setDescription("Sample incident")
            .setCategory("Category")
            .setSeverity("Low")
            .setStatus("New");
        when(incidentMapper.createRequestToModel(request)).thenReturn(incident);

        when(incidentService.createOrUpdateIncident(any())).thenReturn(incident);

        mockMvc.perform(post("/v1/incidents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(request)))
            .andExpect(status().isBadRequest());
    }

    // Test successful update of incident
    @Test
    public void shouldReturnOkWhenUpdateIncidentGivenValidRequest() throws Exception {
        String incidentId = "1";
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setDescription("Updated incident");

        Incident existedIncident = new Incident().setId(Long.parseLong(incidentId))
            .setDescription("Original incident");
        when(incidentService.getIncident(eq(incidentId))).thenReturn(existedIncident);

        Incident updatedIncident = new Incident().setId(Long.parseLong(incidentId))
            .setDescription("Updated incident");
        when(incidentService.createOrUpdateIncident(any())).thenReturn(updatedIncident);

        mockMvc.perform(patch("/v1/incidents/" + incidentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(incidentId))
            .andExpect(jsonPath("$.description").value(updatedIncident.getDescription()));
    }

    // Test update of non-existing incident
    @Test
    public void shouldReturn404WhenUpdateIncidentGivenNonExistingId() throws Exception {
        String incidentId = "2";
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setDescription("Updated incident");

        when(incidentService.getIncident(eq(incidentId)))
            .thenThrow(BusinessException.notFound(ErrorCode.INCIDENT_NOT_FOUND, incidentId));

        mockMvc.perform(patch("/v1/incidents/" + incidentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(request)))
            .andExpect(status().isNotFound());
    }

    // Test successful deletion of incident
    @Test
    public void shouldReturnNoContentWhenDeleteIncidentGivenValidId() throws Exception {
        doNothing().when(incidentService).deleteIncident("1");

        mockMvc.perform(delete("/v1/incidents/1"))
            .andExpect(status().isNoContent());
    }

    // Test successful retrieval of a single incident
    @Test
    public void shouldReturnOkWhenGetIncidentGivenValidId() throws Exception {
        Incident incident = new Incident().setId(1L);
        when(incidentService.getIncident("1")).thenReturn(incident);

        mockMvc.perform(get("/v1/incidents/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"));
    }

    // Test successful retrieval of all incidents
    @Test
    public void shouldReturnOkWhenListAllIncidentsGivenDataExists() throws Exception {
        Incident incident = new Incident().setId(1L);
        when(incidentService.listAllIncidents()).thenReturn(List.of(incident));

        mockMvc.perform(get("/v1/incidents"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"));
    }
}