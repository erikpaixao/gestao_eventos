package com.mirante.gestao_eventos.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.mirante.gestao_eventos.dtos.EventoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String titulo;
    @Column(nullable = false, length = 1000)
    private String descricao;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataEvento;
    @Column(nullable = false, length = 200)
    private String local;
    private boolean ativo;

    public Evento(EventoDTO eventoDTO) {
        this.titulo = eventoDTO.getTitulo();
        this.descricao = eventoDTO.getDescricao();
        this.dataEvento = eventoDTO.getDataEvento();
        this.local = eventoDTO.getLocal();
        this.ativo = true;
    }

}
