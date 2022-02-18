package com.salesianostriana.dam.miarma.security.users.dto;

import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import com.salesianostriana.dam.miarma.security.users.model.UserRole;
import lombok.*;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String nickname;
    private LocalDate fechaNacimiento;
    private String email;
    private String role;
    private String password;
    private String password2;
    private String avatar;
    private boolean publico;
    /*private List<UserEntity> seguidores;
    private List<UserEntity> seguidos;*/


}
