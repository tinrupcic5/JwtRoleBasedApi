package com.o_bee_one.rbac.service.impl;

import com.o_bee_one.rbac.entity.PermissionEntity;
import com.o_bee_one.rbac.entity.RoleEntity;
import com.o_bee_one.rbac.repository.RoleJpaRepository;
import com.o_bee_one.rbac.service.PermissionService;
import com.o_bee_one.rbac.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

  private final RoleJpaRepository roleDao;
  private final PermissionService permissionService;

  public RoleServiceImpl(RoleJpaRepository roleDao, PermissionService permissionService) {
    this.roleDao = roleDao;
    this.permissionService = permissionService;
  }

  @Override
  @Transactional(readOnly = true)
  public RoleEntity findByName(String name) {
    return roleDao.findRoleByName(name);
  }

  @Override
  @Transactional
  public RoleEntity addPermissionToRole(String roleName, PermissionEntity permission) {
    RoleEntity role = findByName(roleName);
    if (role != null) {
      role.getPermissions().add(permission);
      roleDao.save(role);
    }
    return role;
  }
}
