package com.salesianostriana.dam.miarma.users.services;


import com.salesianostriana.dam.miarma.services.base.BaseService;
import com.salesianostriana.dam.miarma.users.dto.CreateUserDto;
import com.salesianostriana.dam.miarma.users.model.UserEntity;
import com.salesianostriana.dam.miarma.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }


    // Este método lo mejoraremos en el próximo tema
    public UserEntity save(CreateUserDto newUser) {
        if (newUser.getPassword1().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
                    .password(passwordEncoder.encode(newUser.getPassword1()))
                    .avatar(newUser.getAvatar())
                    .nickname(newUser.getNickname())
                    .email(newUser.getEmail())
                    .role(newUser.getUserRole())
                    .build();
            return save(userEntity);
        } else {
            return null;
        }
    }


}
