package com.o_bee_one.rbac.service.impl;

import com.o_bee_one.rbac.entity.Role;
import com.o_bee_one.rbac.entity.User;
import com.o_bee_one.rbac.model.UserDto;
import com.o_bee_one.rbac.repository.UserJpaRepository;
import com.o_bee_one.rbac.service.RoleService;
import com.o_bee_one.rbac.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

  private final RoleService roleService;

  private final UserJpaRepository userDao;

  private final BCryptPasswordEncoder bcryptEncoder;

  public UserServiceImpl(
      RoleService roleService, UserJpaRepository userDao, BCryptPasswordEncoder bcryptEncoder) {
    this.roleService = roleService;
    this.userDao = userDao;
    this.bcryptEncoder = bcryptEncoder;
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), getAuthority(user));
  }

  private Set<SimpleGrantedAuthority> getAuthority(User user) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    user.getRoles()
        .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
    return authorities;
  }

  public List<User> findAll() {
    List<User> list = new ArrayList<>();
    userDao.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  @Override
  public User findOne(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public User save(UserDto user) {

    User nUser = user.getUserFromDto();
    nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

    Role role = roleService.findByName("USER");
    Set<Role> roleSet = new HashSet<>();
    roleSet.add(role);

    if (nUser.getEmail().split("@")[1].equals("admin.edu")) {
      role = roleService.findByName("ADMIN");
      roleSet.add(role);
    }

    nUser.setRoles(roleSet);
    return userDao.save(nUser);
  }

  @Override
  public User createEmployee(UserDto user) {
    User nUser = user.getUserFromDto();
    nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

    Role employeeRole = roleService.findByName("EMPLOYEE");
    Role customerRole = roleService.findByName("USER");

    Set<Role> roleSet = new HashSet<>();
    if (employeeRole != null) {
      roleSet.add(employeeRole);
    }
    if (customerRole != null) {
      roleSet.add(customerRole);
    }

    nUser.setRoles(roleSet);
    return userDao.save(nUser);
  }
}
