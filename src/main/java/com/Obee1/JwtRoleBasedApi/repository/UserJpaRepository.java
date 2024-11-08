package com.Obee1.JwtRoleBasedApi.repository;

import com.Obee1.JwtRoleBasedApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}