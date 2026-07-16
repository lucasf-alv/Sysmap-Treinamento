package com.example.demo.web;

import com.example.demo.model.Dono;
import com.example.demo.service.DonoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donos")
public class DonoController {

    private final DonoService donoService;

    public DonoController(DonoService donoService) {
        this.donoService = donoService;
    }

    @PostMapping
    public ResponseEntity<Dono> create(@RequestBody Dono dono) {
        return ResponseEntity.ok(donoService.createDono(dono));
    }
}