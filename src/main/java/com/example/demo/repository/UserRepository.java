package com.example.demo.repository;

import com.example.demo.model.Usuario;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Boolean existsByEmailIgnoreCase(String email);

}
