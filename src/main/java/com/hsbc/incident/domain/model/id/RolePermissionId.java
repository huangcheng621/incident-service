package com.hsbc.incident.domain.model.id;

import com.hsbc.incident.domain.model.value.Permission;
import java.io.Serializable;
import lombok.Data;

@Data
public class RolePermissionId implements Serializable {

    private Long roleId;
    private Permission permission;
}
