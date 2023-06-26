package com.example.outscope.entity.dto;

import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class GoOutRequestDto {

    private Long id;

    private String account;

    private String classNumber;

    private String name;

    private String time;

    private Time departure;

    private Time arrival;

    private String reason;

    private String state;

    private List<Long> member;

}
