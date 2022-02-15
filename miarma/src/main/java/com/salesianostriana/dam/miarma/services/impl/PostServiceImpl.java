package com.salesianostriana.dam.miarma.services.impl;


import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.model.PostRepository;
import com.salesianostriana.dam.miarma.model.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.services.PostService;
import com.salesianostriana.dam.miarma.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final StorageService storageService;

    @Override
    public Post save(CreatePostDto createPostDto, MultipartFile file) {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();


        //Creo Post

        return repository.save(Post.builder()

                .title(createPostDto.getTitle())
                .text(createPostDto.getText())
                .imagen(uri)
                .build());


    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }


}
