package com.salesianostriana.dam.miarma.files.services;

import com.salesianostriana.dam.miarma.files.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.files.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Post save(CreatePostDto createPostDto, MultipartFile file);
    List<Post> findAll();
}