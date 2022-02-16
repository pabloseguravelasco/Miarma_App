package com.salesianostriana.dam.miarma.security.dto;

import com.salesianostriana.dam.miarma.security.users.model.UserRole;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String nickname;
    private LocalDate fechaNacimiento;
    private String email;
    private boolean publico;
    private String password;
    private String password2;
    private String avatar;
    private String token;
    private UserRole role;

}
