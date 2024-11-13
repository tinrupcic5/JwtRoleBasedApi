package com.o_bee_one.rbac.service;

import com.o_bee_one.rbac.entity.User;
import com.o_bee_one.rbac.model.UserDto;
import java.util.List;

public interface UserService {

  User save(UserDto user);

  List<User> findAll();

  User findOne(String username);

  User createEmployee(UserDto user);
}
