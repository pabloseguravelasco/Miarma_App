package com.salesianostriana.dam.miarma.files.controller;


import com.salesianostriana.dam.miarma.files.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.files.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newPost) {


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(newPost, file));
    }

    @GetMapping("/")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findAll());
    }



}
