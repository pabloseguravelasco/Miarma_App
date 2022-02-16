package com.salesianostriana.dam.miarma.model;

import com.salesianostriana.dam.miarma.security.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPublico(Boolean publico);

    List<Post> findByUserNickname(String nickname);

}
