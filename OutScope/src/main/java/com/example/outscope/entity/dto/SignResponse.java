package com.example.outscope.entity.dto;


import com.example.outscope.entity.Authority;
import com.example.outscope.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {

    private Long id;

    private String account;

    private String username;

    private String email;

    private String job;

    private String classNumber;

    private List<Authority> roles = new ArrayList<>();

    private String token;

    public SignResponse(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.username = user.getUsername();
        this.job = user.getJob();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.classNumber = user.getClassNumber();
    }


}
