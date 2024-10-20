package com.hsbc.incident.api.controller;

import com.hsbc.incident.api.mapper.IncidentMapper;
import com.hsbc.incident.api.request.CreateIncidentRequest;
import com.hsbc.incident.api.request.UpdateIncidentRequest;
import com.hsbc.incident.domain.model.entity.Incident;
import com.hsbc.incident.domain.service.IncidentService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;
    private final IncidentMapper incidentMapper;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Incident createIncident(@Valid @RequestBody CreateIncidentRequest request) {
        Incident incident = incidentMapper.createRequestToModel(request);
        return incidentService.createOrUpdateIncident(incident);
    }

    @PatchMapping("/{incidentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Incident updateIncident(@PathVariable("incidentId") String IncidentId,
        @Valid @RequestBody UpdateIncidentRequest request) {
        Incident existedIncident = incidentService.getIncident(IncidentId);
        incidentMapper.updateModelFromRequest(request, existedIncident);
        return incidentService.createOrUpdateIncident(existedIncident);
    }

    @DeleteMapping("/{incidentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteIncident(@PathVariable("incidentId") String IncidentId) {
        incidentService.deleteIncident(IncidentId);
    }

    @GetMapping("/{incidentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Incident getIncident(@PathVariable("incidentId") String IncidentId) {
        return incidentService.getIncident(IncidentId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Incident> listAllIncidents() {
        return incidentService.listAllIncidents();
    }
}
