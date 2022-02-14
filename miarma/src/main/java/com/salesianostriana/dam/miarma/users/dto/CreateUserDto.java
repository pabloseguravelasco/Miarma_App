package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.users.model.UserRole;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String nickname;
    private LocalDate fechaNacimiento;
    private String email;
    private boolean tipoUsuario;
    private UserRole userRole;
    private String password1;
    private String password2;
    private String avatar;

}