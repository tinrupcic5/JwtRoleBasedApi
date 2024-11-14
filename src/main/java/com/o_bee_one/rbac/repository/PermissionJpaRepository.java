package com.o_bee_one.rbac.repository;

import com.o_bee_one.rbac.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long> {
  PermissionEntity findByDescription(String name);
}
