package com.salesianostriana.dam.miarma.security.users.dto;

import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {

        String uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(user.getAvatar())
                .toUriString();

        return GetUserDto.builder()
                .nickname(user.getNickname())
                .fechaNacimiento(user.getFechaNacimiento())
                .email(user.getEmail())
                .role(user.getRole().name())
                .isPublic(user.isPublic())
                .password(user.getPassword())
                .avatar(uri)
                .build();


    }

}