package com.o_bee_one.rbac.service.impl;

import com.o_bee_one.rbac.entity.Role;
import com.o_bee_one.rbac.repository.RoleRepository;
import com.o_bee_one.rbac.service.RoleService;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleDao;

    public RoleServiceImpl(RoleRepository roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findRoleByName(name);
    }
}
