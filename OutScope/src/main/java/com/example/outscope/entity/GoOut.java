package com.example.outscope.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classNumber;

    private String name;

    private String time;

    private Time departure;

    private Time arrival;

    private String reason;

    private String state;

    @OneToMany
    private List<User> member;

}
