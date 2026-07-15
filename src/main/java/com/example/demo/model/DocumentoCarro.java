package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "documento_carro")
@Entity
@AllArgsConstructor
public class DocumentoCarro {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "renavam", nullable = false)
    private String renavam;
    @Column(name = "numero_documento", nullable = false)
    private String numero_documento;
    @OneToOne
    @JoinColumn(name = "carro_id")
    private Car carro;


}
