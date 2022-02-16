package com.salesianostriana.dam.miarma.model.dto;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.security.users.dto.GetUserDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class PostDtoConverter {

    public GetPostDto convertPostToGetPostDto(Post post, UserEntity user) {

        String uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(post.getImagen())
                .toUriString();

        return GetPostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .userNickname(user.getNickname())
                .publico(post.isPublico())
                .imagen(uri)
                .build();


    }

    public GetPostDto convertListPostToListGetPostDto(Post post) {

        String uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(post.getImagen())
                .toUriString();

        return GetPostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .userNickname(post.getUser().getNickname())
                .publico(post.isPublico())
                .imagen(uri)
                .build();


    }

}
