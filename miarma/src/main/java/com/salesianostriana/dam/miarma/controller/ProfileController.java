package com.salesianostriana.dam.miarma.controller;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.model.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.model.dto.GetPostDto;
import com.salesianostriana.dam.miarma.security.dto.JwtUserResponse;
import com.salesianostriana.dam.miarma.security.users.dto.CreateUserDto;
import com.salesianostriana.dam.miarma.security.users.dto.GetUserDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.security.users.repository.UserEntityRepository;
import com.salesianostriana.dam.miarma.security.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserEntityRepository userEntityRepository;
    private final UserEntityService userEntityService;


    @GetMapping("/{id}")


   @PutMapping("/me")
    public ResponseEntity<Optional<GetUserDto>> updateUser (@PathVariable UUID id, @RequestPart("file") MultipartFile file,
                                                            @RequestPart("user") CreateUserDto createuserDto, @AuthenticationPrincipal UserEntity user) throws Exception{

            return ResponseEntity.ok().body(userEntityService.updateUser(id, file, createuserDto, user));
    }
}




