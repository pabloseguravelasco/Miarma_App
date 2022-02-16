package com.salesianostriana.dam.miarma.model;

import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String text;
    private String imagen;
    private boolean publico;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
