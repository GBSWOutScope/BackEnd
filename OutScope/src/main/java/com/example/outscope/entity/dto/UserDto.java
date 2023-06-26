package com.example.outscope.entity.dto;

import com.example.outscope.entity.User;
import lombok.Data;

@Data
public class UserDto {

    private String username;

    private String classNumber;

    public static UserDto userToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setClassNumber(user.getClassNumber());
        return dto;
    }

}
