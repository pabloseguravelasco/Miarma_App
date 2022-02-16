package com.salesianostriana.dam.miarma.security.users.dto;

import com.salesianostriana.dam.miarma.model.dto.GetPostDto;
import com.salesianostriana.dam.miarma.security.users.model.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String nickname;
    private LocalDate fechaNacimiento;
    private String email;
    private String password;
    private String password2;
    private String avatar;
    private boolean publico;

}