package com.Obee1.JwtRoleBasedApi.service;


import com.Obee1.JwtRoleBasedApi.entity.User;
import com.Obee1.JwtRoleBasedApi.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    List<User> findAll();

    User findOne(String username);

    User createEmployee(UserDto user);

}