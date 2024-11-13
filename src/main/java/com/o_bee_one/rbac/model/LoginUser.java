package com.o_bee_one.rbac.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginUser {
    private String username;
    private String password;

}