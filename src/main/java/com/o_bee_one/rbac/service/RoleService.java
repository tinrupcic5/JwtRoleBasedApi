package com.o_bee_one.rbac.service;


import com.o_bee_one.rbac.entity.Role;

public interface RoleService {
    Role findByName(String name);
}