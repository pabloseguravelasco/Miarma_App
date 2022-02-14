package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .userRole(user.getRole())
                .build();


    }

}