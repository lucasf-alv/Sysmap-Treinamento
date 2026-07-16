package com.example.demo.service;

import com.example.demo.model.Dono;
import com.example.demo.repository.DonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonoService {

    private final DonoRepository donoRepository;

    public Dono createDono(Dono dono){
        return donoRepository.save(dono);
    }
}
