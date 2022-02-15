package com.salesianostriana.dam.miarma.security.users.dto;

import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole())
                .isPublic(user.isPublic())
                .password(user.getPassword())
                .fechaNacimiento(user.getFechaNacimiento())
                .avatar(user.getAvatar())
                .build();


    }

}