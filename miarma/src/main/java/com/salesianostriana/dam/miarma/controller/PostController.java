package com.salesianostriana.dam.miarma.controller;


import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.model.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.model.dto.GetPostDto;
import com.salesianostriana.dam.miarma.model.dto.PostDtoConverter;
import com.salesianostriana.dam.miarma.security.users.dto.GetUserDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.security.users.model.UserRole;
import com.salesianostriana.dam.miarma.services.PostService;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor

public class PostController {


    private final PostService service;
    private final PostDtoConverter postDtoConverter;


    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newPost,
                                    @AuthenticationPrincipal UserEntity user) {

        Post postCreated = service.save(newPost, file, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoConverter.convertPostToGetPostDto(postCreated, user));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newPost,
                                    @AuthenticationPrincipal UserEntity user) {

        Post postUpdate = service.save(newPost, file, user);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/public")
    public ResponseEntity<?> findByIsPublic() {
        return ResponseEntity.ok(service.findByPublico(true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto> findPostByID(@PathVariable Long id, @AuthenticationPrincipal UserEntity user){

        Optional<Post> postOptional = service.findPostByID(id);

        if(postOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok().body(postDtoConverter.convertPostToGetPostDto(postOptional.get(),user));
    }

    @GetMapping("/")
    public ResponseEntity<List<GetPostDto>> findByUserNickname(@RequestParam(value = "nickname") String nickname){

        if (nickname.isBlank()){
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.ok().body(service.listPostDto(nickname));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GetPostDto>> findAllPostUserCurrent(@AuthenticationPrincipal UserEntity user){

        if (user.getId() == null){
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.ok().body(service.listPostDto(user.getNickname()));
    }



}


