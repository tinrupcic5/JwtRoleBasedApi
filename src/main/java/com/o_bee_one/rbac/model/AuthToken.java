package com.o_bee_one.rbac.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthToken {
  private String token;

  public AuthToken(String token) {
    this.token = token;
  }
}
