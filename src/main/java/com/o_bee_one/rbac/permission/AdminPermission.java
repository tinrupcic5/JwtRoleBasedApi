package com.o_bee_one.rbac.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@PreAuthorize("hasRole('ADMIN') and hasAuthority('PERMISSION_WRITE') and hasAuthority('PERMISSION_READ')")
public @interface AdminPermission {}
