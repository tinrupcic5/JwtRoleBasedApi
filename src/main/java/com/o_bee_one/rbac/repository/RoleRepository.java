package com.o_bee_one.rbac.repository;

import com.o_bee_one.rbac.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  RoleEntity findRoleByName(String name);
}
