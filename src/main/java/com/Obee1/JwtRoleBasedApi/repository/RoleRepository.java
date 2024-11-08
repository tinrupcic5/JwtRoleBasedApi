package com.Obee1.JwtRoleBasedApi.repository;

import com.Obee1.JwtRoleBasedApi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}