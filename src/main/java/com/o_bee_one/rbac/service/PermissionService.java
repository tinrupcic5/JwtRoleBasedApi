package com.o_bee_one.rbac.service;

import com.o_bee_one.rbac.entity.PermissionEntity;
import java.util.List;

public interface PermissionService {
  PermissionEntity createPermission(String name, String description);

  PermissionEntity findByName(String name);

  List<PermissionEntity> findAll();
}
