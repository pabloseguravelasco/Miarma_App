package com.salesianostriana.dam.miarma.files.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePostDto {

    private String title;
    private String text;
    private String imagen;
}