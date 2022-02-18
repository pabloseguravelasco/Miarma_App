package com.salesianostriana.dam.miarma.security.users.services;


import com.salesianostriana.dam.miarma.security.users.dto.GetUserDto;
import com.salesianostriana.dam.miarma.security.users.dto.UserDtoConverter;
import com.salesianostriana.dam.miarma.security.users.model.UserRole;
import com.salesianostriana.dam.miarma.services.StorageService;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import com.salesianostriana.dam.miarma.security.users.dto.CreateUserDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.security.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final UserEntityRepository userEntityRepository;
    private final UserDtoConverter userDtoConverter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " no encontrado"));
    }


    public UserEntity save(CreateUserDto newUser, MultipartFile file) {


        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {

            String filename = storageService.store(file);

            String uri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString();

            UserEntity userEntity = UserEntity.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(uri)
                    .nickname(newUser.getNickname())
                    .fechaNacimiento(newUser.getFechaNacimiento())
                    .email(newUser.getEmail())
                    .publico(newUser.isPublico())
                    .role(UserRole.USER)
                    .build();
            return save(userEntity);
        } else {
            return null;
        }
    }

    @Override
    public GetUserDto updateUser( MultipartFile file,
                                           CreateUserDto createuserDto,  UserEntity user) throws Exception {

        if (file.isEmpty()) {

            user.setNickname(createuserDto.getNickname());
            user.setEmail(createuserDto.getEmail());
            user.setPassword(createuserDto.getPassword());
            user.setPublico(createuserDto.isPublico());
            user.setNickname(createuserDto.getNickname());
            user.setFechaNacimiento(createuserDto.getFechaNacimiento());
            user.setAvatar(createuserDto.getAvatar());

            userEntityRepository.save(user);
            return userDtoConverter.convertUserEntityToGetUserDto(user);


        } else {


            String name = StringUtils.cleanPath(String.valueOf(user.getAvatar())).replace("http://localhost:8080/download/", "");

            Path pa = storageService.load(name);

            String filename = StringUtils.cleanPath(String.valueOf(pa)).replace("http://localhost:8080/download/", "");

            Path path = Paths.get(filename);

            storageService.delete(user.getAvatar());


            String newFilename = storageService.store(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(newFilename)
                    .toUriString();

            user.setNickname(createuserDto.getNickname());
            user.setEmail(createuserDto.getEmail());
            user.setPassword(createuserDto.getPassword());
            user.setPublico(createuserDto.isPublico());
            user.setNickname(createuserDto.getNickname());
            user.setFechaNacimiento(createuserDto.getFechaNacimiento());
            user.setAvatar(uri);

            userEntityRepository.save(user);
            return userDtoConverter.convertUserEntityToGetUserDto(user);


        }
    }


}
