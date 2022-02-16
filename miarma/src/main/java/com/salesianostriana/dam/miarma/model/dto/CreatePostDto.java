package com.salesianostriana.dam.miarma.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostDto {

    private String title;
    private String text;
    private boolean isPublic;


}