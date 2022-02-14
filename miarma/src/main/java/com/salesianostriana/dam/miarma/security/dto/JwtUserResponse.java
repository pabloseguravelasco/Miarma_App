package com.salesianostriana.dam.miarma.security.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String nickname;
    private LocalDate fechaNacimiento;
    private String email;
    private boolean tipoUsuario;
    private String password1;
    private String password2;
    private String avatar;
    private String token;

}
