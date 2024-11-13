package com.o_bee_one.rbac.controller;

import com.o_bee_one.rbac.config.TokenProvider;
import com.o_bee_one.rbac.entity.UserEntity;
import com.o_bee_one.rbac.model.AuthToken;
import com.o_bee_one.rbac.model.LoginUser;
import com.o_bee_one.rbac.model.UserDto;
import com.o_bee_one.rbac.permission.AdminPermission;
import com.o_bee_one.rbac.permission.UserReadPermission;
import com.o_bee_one.rbac.permission.UserWritePermission;
import com.o_bee_one.rbac.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

  private final AuthenticationManager authenticationManager;

  private final TokenProvider jwtTokenUtil;

  private final UserService userService;

  public UserController(
      AuthenticationManager authenticationManager,
      TokenProvider jwtTokenUtil,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userService = userService;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthToken> generateToken(@RequestBody LoginUser loginUser)
      throws AuthenticationException {
    final Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginUser.getUsername(), loginUser.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtTokenUtil.generateToken(authentication);
    return ResponseEntity.ok(new AuthToken(token));
  }

  @PostMapping("/register")
  public UserEntity saveUser(@RequestBody UserDto user) {
    return userService.save(user);
  }

  @GetMapping("/adminping")
  @AdminPermission
  public String adminPing() {
    return "Only Admins Can Read This";
  }

  @GetMapping("/userping")
  @UserReadPermission
  public String userPing() {
    return "Any User Can Read This";
  }
  @GetMapping("/userwrite")
  @UserWritePermission
  public String userWrite() {
    return "Any User Can Write This";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create/employee")
  public UserEntity createEmployee(@RequestBody UserDto user) {
    return userService.createEmployee(user);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/find/all")
  public List<UserEntity> getAllList() {
    return userService.findAll();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/find/by/username")
  public UserEntity getAllList(@RequestParam String username) {
    return userService.findOne(username);
  }
}
