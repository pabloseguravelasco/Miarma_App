package com.salesianostriana.dam.miarma.services.impl;


import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.model.PostRepository;
import com.salesianostriana.dam.miarma.model.dto.CreatePostDto;
import com.salesianostriana.dam.miarma.model.dto.GetPostDto;
import com.salesianostriana.dam.miarma.model.dto.PostDtoConverter;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.services.PostService;
import com.salesianostriana.dam.miarma.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final StorageService storageService;
    private final PostDtoConverter postDtoConverter;
    private final PostRepository postRepository;

    @Override
    public Post save(CreatePostDto createPostDto, MultipartFile file, UserEntity user) throws IOException {

        String filename = storageService.store(file);
        String filenameScaled = storageService.store(file);

        String extension = StringUtils.getFilenameExtension(filename);
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage imageScaled = storageService.scaledImg(originalImage,1024);
        OutputStream outputStream = Files.newOutputStream(storageService.load(filename));
        ImageIO.write(imageScaled,extension, outputStream);



        String uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();


        return repository.save(Post.builder()
                .publico(createPostDto.isPublico())
                .title(createPostDto.getTitle())
                .text(createPostDto.getText())
                .user(user)
                .imagen(uri)
                .build());


    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public  List<GetPostDto> findByPublico(boolean publico) {

        List<Post> listaPost = repository.findByPublico(publico);

        return listaPost.stream().map(postDtoConverter::convertListPostToListGetPostDto).collect(Collectors.toList());
    }

    @Override
    public Optional<Post> findPostByID(Long id){

        return  repository.findById(id);
    }

    @Override
    public List<Post> findByUserNickname(String nickname){

        return repository.findByUserNickname(nickname);
    }

    @Override
    public List<GetPostDto> listPostDto(String nickname){

        List<Post> listaPost = repository.findByUserNickname(nickname);

       return listaPost.stream().map(postDtoConverter::convertListPostToListGetPostDto).collect(Collectors.toList());
    }

    @Override
    public Optional<GetPostDto> updatePost (@PathVariable Long id,@RequestPart("file") MultipartFile file,
                                        @RequestPart("post") CreatePostDto createPostDto, @AuthenticationPrincipal  UserEntity user) throws Exception{

        if (file.isEmpty()){

        Optional<Post> post = postRepository.findById(id);

        return post.map(m -> {
            m.setTitle(createPostDto.getTitle());
            m.setText(createPostDto.getText());
            m.setPublico(createPostDto.isPublico());
            m.setImagen(m.getImagen());
            postRepository.save(m);
            return postDtoConverter.convertPostToGetPostDto(m, user);
        });

    }else{

        Optional<Post> post = postRepository.findById(id);

        String name = StringUtils.cleanPath(String.valueOf(post.get().getImagen())).replace("http://localhost:8080/download/", "");

        Path pa = storageService.load(name);

        String filename = StringUtils.cleanPath(String.valueOf(pa)).replace("http://localhost:8080/download/", "");

        Path path = Paths.get(filename);

        storageService.delete(post.get().getImagen());


        String newFilename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(newFilename)
                .toUriString();

        return post.map(m -> {
            m.setTitle(createPostDto.getTitle());
            m.setText(createPostDto.getText());
            m.setImagen(uri);
            m.setPublico(createPostDto.isPublico());
            postRepository.save(m);
            return postDtoConverter.convertPostToGetPostDto(m, user);

        });
    }
}









}
