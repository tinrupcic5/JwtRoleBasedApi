package com.o_bee_one.rbac.service;

import com.o_bee_one.rbac.entity.PermissionEntity;
import com.o_bee_one.rbac.entity.RoleEntity;

public interface RoleService {
  RoleEntity findByName(String name);

  RoleEntity addPermissionToRole(String roleName, PermissionEntity permission);
}
