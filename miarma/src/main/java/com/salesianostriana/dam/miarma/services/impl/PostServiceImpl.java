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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final StorageService storageService;
    private final PostDtoConverter postDtoConverter;

    @Override
    public Post save(CreatePostDto createPostDto, MultipartFile file, UserEntity user) {

        String filename = storageService.store(file);

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


    /*@Override
    public void delete(Long id) throws IOException {

        Optional<Post> postOptional = repository.findById(id);
        String imageName = StringUtils.cleanPath(String.valueOf(postOptional.get().getImagen()))
                .replace(".jpg","");

        Path path = storageService.load(imageName);

        String filename = StringUtils.cleanPath(String.valueOf(path))
                .replace(".jpg", "");

        storageService.deleteFile(filename);
        repository.deleteById(id);
    }




    @Override
    public List<Post> findAll() {

        return repository.findAll();
    }

    @Override
    public List<Post> findAllPostByUser() {
        return null;
    }

    @Override
    public GetPostDto findOnePost(@PathVariable  Long id, @AuthenticationPrincipal UserEntity user){

        Optional<Post> postOptional = repository.findById(id);

        if (repository.findById(id).isEmpty()) {
            return null;

        } else {
            GetPostDto getPostDto = postDtoConverter.convertPostToGetPostDto(postOptional.get(),user);
            return null;
        }
    }*/




}
