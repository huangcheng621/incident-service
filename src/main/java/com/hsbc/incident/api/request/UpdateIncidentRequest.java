package com.hsbc.incident.api.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateIncidentRequest {

    private String name;
    private String description;
    private String status;
    private String severity;
    private String category;
}
