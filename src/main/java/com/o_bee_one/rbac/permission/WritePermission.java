package com.o_bee_one.rbac.permission;

import java.lang.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@PreAuthorize("hasAuthority('WRITE')")
public @interface WritePermission {}
