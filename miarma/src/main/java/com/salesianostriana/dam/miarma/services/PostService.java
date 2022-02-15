package com.salesianostriana.dam.miarma.services;


import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.model.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.model.dto.GetPostDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {
    Post save(CreatePostDto createPostDto, MultipartFile file);
    List<Post> findAll();

    //Post edit(GetPostDto getPostDto, MultipartFile file);

}