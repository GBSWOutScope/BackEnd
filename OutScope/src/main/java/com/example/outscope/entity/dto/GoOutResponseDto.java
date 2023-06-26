package com.example.outscope.entity.dto;

import com.example.outscope.entity.GoOut;
import com.example.outscope.entity.User;
import lombok.Data;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
public class GoOutResponseDto {

    private Long id;

    private String classNumber;

    private String name;

    private String time;

    private Time departure;

    private Time arrival;

    private String reason;

    private String state;

    private List<UserDto> member;

    public static GoOutResponseDto entityToDto(GoOut entity) {
        GoOutResponseDto responseDto = new GoOutResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setClassNumber(entity.getClassNumber());
        responseDto.setName(entity.getName());
        responseDto.setTime(entity.getTime());
        responseDto.setDeparture(entity.getDeparture());
        responseDto.setArrival(entity.getArrival());
        responseDto.setReason(entity.getReason());
        responseDto.setState(entity.getState());

        List<UserDto> userDtos = new ArrayList<>();

        for (User member : entity.getMember()) {
            userDtos.add(UserDto.userToDto(member));
        }

        responseDto.setMember(userDtos);

        return responseDto;
    }

}
