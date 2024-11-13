package com.o_bee_one.rbac.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "app_role")
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column private String name;

  @Column private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "ROLE_PERMISSIONS",
      joinColumns = @JoinColumn(name = "ROLE_ID"),
      inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
  private Set<PermissionEntity> permissions = new HashSet<>();
}
