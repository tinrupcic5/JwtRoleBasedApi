package com.o_bee_one.rbac.permission;

import lombok.Getter;

@Getter
public enum Permission {
  READ("READ"),
  WRITE("WRITE");

  private final String name;

  Permission(String name) {
    this.name = name;
  }
}
