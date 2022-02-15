package com.salesianostriana.dam.miarma.security.users.services;


import com.salesianostriana.dam.miarma.security.users.model.UserRole;
import com.salesianostriana.dam.miarma.services.StorageService;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import com.salesianostriana.dam.miarma.security.users.dto.CreateUserDto;
import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.security.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
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
                    .role(UserRole.USER)
                    .build();
            return save(userEntity);
        } else {
            return null;
        }
    }


}
