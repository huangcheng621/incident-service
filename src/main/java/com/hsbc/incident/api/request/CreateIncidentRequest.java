package com.hsbc.incident.api.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CreateIncidentRequest {

    @NotBlank
    private String name;
    private String description;
    private String status;
    private String severity;
    private String category;
}
