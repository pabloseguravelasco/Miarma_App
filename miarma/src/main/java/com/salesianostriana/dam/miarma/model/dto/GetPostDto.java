package com.salesianostriana.dam.miarma.model.dto;

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
    private boolean publico;
}
