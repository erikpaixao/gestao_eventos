package com.mirante.gestao_eventos.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.mirante.gestao_eventos.dtos.EventoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String titulo;
    private String descricao;
    private LocalDateTime dataEvento;
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
