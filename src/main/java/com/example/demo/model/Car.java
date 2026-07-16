package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class Car{
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "marca" , nullable = true)
    private String marca;
    @Column(name = "modelo" , nullable = true)
    private String modelo;
    @Column(name = "ano" , nullable = true)
    private String ano;
    @Column(name = "placa" , nullable = true, unique = true)
    private String placa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dono_id", nullable = false)
    private Dono dono;
    @OneToOne(mappedBy = "carro", cascade = CascadeType.ALL, orphanRemoval = true)
    private DocumentoCarro documentoCarro;
    @ManyToMany
    @JoinTable(
            name = "carro_acessorio",
            joinColumns = @JoinColumn(name = "carro_id"),
            inverseJoinColumns = @JoinColumn(name = "acessorio_id")
    )
    private List<Acessorio> acessorios = new ArrayList<>();
    @Column(name="photo_id")
    private String photo_id;

    @Column(name= "photo_url", length = 1000)
    private String photo_url;

}

