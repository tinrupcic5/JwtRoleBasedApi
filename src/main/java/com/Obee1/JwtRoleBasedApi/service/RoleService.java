package com.Obee1.JwtRoleBasedApi.service;


import com.Obee1.JwtRoleBasedApi.entity.Role;

public interface RoleService {
    Role findByName(String name);
}