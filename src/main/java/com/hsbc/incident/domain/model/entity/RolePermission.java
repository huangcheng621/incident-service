package com.hsbc.incident.domain.model.entity;

import com.hsbc.incident.domain.model.id.RolePermissionId;
import com.hsbc.incident.domain.model.value.Permission;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
@IdClass(RolePermissionId.class)
@Table(name = "role_permissions")
public class RolePermission {

    @Id
    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    @Id
    @Enumerated(EnumType.STRING)
    private Permission permission;
}
