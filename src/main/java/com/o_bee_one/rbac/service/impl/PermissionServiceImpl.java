package com.o_bee_one.rbac.service.impl;

import com.o_bee_one.rbac.entity.PermissionEntity;
import com.o_bee_one.rbac.repository.PermissionJpaRepository;
import com.o_bee_one.rbac.service.PermissionService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

  private final PermissionJpaRepository permissionJpaRepository;

  public PermissionServiceImpl(PermissionJpaRepository permissionJpaRepository) {
    this.permissionJpaRepository = permissionJpaRepository;
  }

  @Override
  public PermissionEntity createPermission(String name, String description) {
    return permissionJpaRepository.save(new PermissionEntity(name, description));
  }

  @Override
  public PermissionEntity findByName(String name) {
    return permissionJpaRepository.findByDescription(name);
  }

  @Override
  public List<PermissionEntity> findAll() {
    return permissionJpaRepository.findAll();
  }
}
