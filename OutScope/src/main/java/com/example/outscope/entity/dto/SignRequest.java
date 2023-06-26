package com.example.outscope.entity.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignRequest {

    private Long id;

    private String account;

    private String password;

    private String username;

    private String job;

    private String email;

    private String classNumber;

}
