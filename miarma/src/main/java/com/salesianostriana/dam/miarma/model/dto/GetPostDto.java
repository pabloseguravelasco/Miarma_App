package com.salesianostriana.dam.miarma.model.dto;

import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostDto {

    private Long id;
    private String title;
    private String text;
    private String imagen;
    private String userNickname;
}
