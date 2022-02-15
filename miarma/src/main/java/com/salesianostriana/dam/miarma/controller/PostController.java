package com.salesianostriana.dam.miarma.controller;


import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.model.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.model.dto.PostDtoConverter;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor

public class PostController {

    private final PostService service;
    private final PostDtoConverter postDtoConverter;


    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newPost,
                                    @AuthenticationPrincipal UserEntity user)
    {

        Post postCreated = service.save(newPost,file);

        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoConverter.convertPostToGetPostDto(postCreated, user));

    }

   /* @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestPart("file") MultipartFile file,
                                  @RequestPart("post") CreatePostDto newPost,
                                  @AuthenticationPrincipal UserEntity user) {

        Post postUpdate = service.save(newPost, file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoConverter.convertPostToGetPostDto(postUpdate, user));

    }*/

    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestPart("file") MultipartFile file,
                                  @RequestPart("post") CreatePostDto newPost,
                                  @AuthenticationPrincipal UserEntity user) {

        Post postUpdate = service.save(newPost, file);

        return ResponseEntity.noContent().build();

    }*/



    @GetMapping("/")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findAll());
    }

}
