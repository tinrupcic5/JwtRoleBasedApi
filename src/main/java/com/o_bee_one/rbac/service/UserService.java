package com.o_bee_one.rbac.service;

import com.o_bee_one.rbac.entity.UserEntity;
import com.o_bee_one.rbac.model.UserDto;
import java.util.List;

public interface UserService {

  UserEntity save(UserDto user);

  List<UserEntity> findAll();

  UserEntity findOne(String username);

  UserEntity createEmployee(UserDto user);
}
