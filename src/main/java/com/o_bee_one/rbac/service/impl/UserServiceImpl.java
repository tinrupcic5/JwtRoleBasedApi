package com.o_bee_one.rbac.service.impl;

import com.o_bee_one.rbac.entity.PermissionEntity;
import com.o_bee_one.rbac.entity.RoleEntity;
import com.o_bee_one.rbac.entity.UserEntity;
import com.o_bee_one.rbac.model.UserDto;
import com.o_bee_one.rbac.repository.UserJpaRepository;
import com.o_bee_one.rbac.service.PermissionService;
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
    private final PermissionService permissionService;

    public UserServiceImpl(
            RoleService roleService,
            UserJpaRepository userDao,
            BCryptPasswordEncoder bcryptEncoder,
            PermissionService permissionService) {
        this.roleService = roleService;
        this.userDao = userDao;
        this.bcryptEncoder = bcryptEncoder;
        this.permissionService = permissionService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(), userEntity.getPassword(), getAuthority(userEntity));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity userEntity) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        userEntity
                .getRoleEntities()
                .forEach(
                        role -> {
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                            role.getPermissions()
                                    .forEach(
                                            permission ->
                                                    authorities.add(new SimpleGrantedAuthority("PERMISSION_" + permission.getName())));
                        });
        return authorities;
    }

    public List<UserEntity> findAll() {
        List<UserEntity> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public UserEntity findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserEntity save(UserDto user) {
        UserEntity nUserEntity = user.getUserFromDto();
        nUserEntity.setPassword(bcryptEncoder.encode(user.getPassword()));

        RoleEntity roleEntity = roleService.findByName("USER");
        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);

        roleEntity = roleService.findByName("USER");

        PermissionEntity writePermission = permissionService.findByName("WRITE");
        roleService.addPermissionToRole(roleEntity.getName(), writePermission);
        roleEntitySet.add(roleEntity);

        nUserEntity.setRoleEntities(roleEntitySet);
        return userDao.save(nUserEntity);
    }

    @Override
    public UserEntity createEmployee(UserDto user) {
        UserEntity nUserEntity = user.getUserFromDto();
        nUserEntity.setPassword(bcryptEncoder.encode(user.getPassword()));

        RoleEntity employeeRoleEntity = roleService.findByName("EMPLOYEE");
        RoleEntity customerRoleEntity = roleService.findByName("USER");

        Set<RoleEntity> roleEntitySet = new HashSet<>();
        if (employeeRoleEntity != null) {
            roleEntitySet.add(employeeRoleEntity);
        }
        if (customerRoleEntity != null) {
            roleEntitySet.add(customerRoleEntity);
        }

        nUserEntity.setRoleEntities(roleEntitySet);
        return userDao.save(nUserEntity);
    }
}
