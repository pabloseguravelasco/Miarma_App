package com.salesianostriana.dam.miarma.services;


import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.model.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.model.dto.GetPostDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface PostService {

    Post save(CreatePostDto createPostDto, MultipartFile file, UserEntity user);

    List<Post> findAll();

    List<Post> findByPublico(boolean publico);

    Optional<Post> findPostByID(Long id);

    List<Post> findByUserNickname(String nickname);

    List<GetPostDto> listPostDto(String nickname);

}