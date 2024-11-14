package com.o_bee_one.rbac.repository;

import com.o_bee_one.rbac.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByUsername(String username);
}
