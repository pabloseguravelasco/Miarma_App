package com.salesianostriana.dam.miarma.security.users.controller;

import com.salesianostriana.dam.miarma.security.users.dto.CreateUserDto;
import com.salesianostriana.dam.miarma.security.users.dto.GetUserDto;
import com.salesianostriana.dam.miarma.security.users.dto.UserDtoConverter;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.security.users.services.UserEntityService;

import com.salesianostriana.dam.miarma.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final StorageService storageService;

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestPart("user") CreateUserDto newUser,
                                                   @RequestPart("file") MultipartFile file) throws IOException {

        UserEntity saved = userEntityService.save(newUser, file);
        String filename = storageService.store(file);

        String extension = StringUtils.getFilenameExtension(filename);
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage imageScaled = storageService.scaledImg(originalImage,128);
        OutputStream outputStream = Files.newOutputStream(storageService.load(filename));
        ImageIO.write(imageScaled,extension, outputStream);


        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }


}
