package com.example.springsecurity.entity;

import lombok.Data;
import lombok.experimental.Accessors;


import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TestUser implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String authority;
    private Date created;
    private Date updated;
}
