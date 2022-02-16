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


public interface PostService {
    Post save(CreatePostDto createPostDto, MultipartFile file);

    List<Post> findAll();

    List<Post>findAllPostByUser();

    void delete(Long id) throws IOException;


    ResponseEntity<GetPostDto> findOnePost(@PathVariable Long id, @AuthenticationPrincipal UserEntity user);
}