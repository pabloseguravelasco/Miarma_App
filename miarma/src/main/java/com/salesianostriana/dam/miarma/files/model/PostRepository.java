package com.salesianostriana.dam.miarma.files.model;

import com.salesianostriana.dam.miarma.files.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
