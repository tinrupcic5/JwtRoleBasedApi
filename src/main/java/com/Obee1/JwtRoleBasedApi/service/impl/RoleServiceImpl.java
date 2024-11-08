package com.Obee1.JwtRoleBasedApi.service.impl;

import com.Obee1.JwtRoleBasedApi.entity.Role;
import com.Obee1.JwtRoleBasedApi.repository.RoleRepository;
import com.Obee1.JwtRoleBasedApi.service.RoleService;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleDao;

    public RoleServiceImpl(RoleRepository roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }
}
