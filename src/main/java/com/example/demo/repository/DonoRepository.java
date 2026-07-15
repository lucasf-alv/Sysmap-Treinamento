package com.example.demo.repository;

import com.example.demo.model.Dono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonoRepository extends JpaRepository<Dono,Long> {
    Object existsByCpf(String number);
}
