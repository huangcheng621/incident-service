package com.hsbc.incident.api.mapper;

import com.hsbc.incident.api.request.CreateIncidentRequest;
import com.hsbc.incident.api.request.UpdateIncidentRequest;
import com.hsbc.incident.domain.model.entity.Incident;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IncidentMapper {

    Incident createRequestToModel(CreateIncidentRequest request);

    void updateModelFromRequest(UpdateIncidentRequest request, @MappingTarget Incident incident);
}
