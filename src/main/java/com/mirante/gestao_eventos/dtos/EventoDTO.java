package com.mirante.gestao_eventos.dtos;

import java.time.LocalDateTime;

import com.mirante.gestao_eventos.models.Evento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventoDTO {

    private Long id;
    @Size(max = 100, min = 3)
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    @Size(max = 1000, min = 10)
    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;
    @Future
    @NotBlank(message = "Data do evento é obrigatório")
    private LocalDateTime dataEvento;
    @Size(max = 200, min = 3)
    @NotBlank(message = "Local do evento é obrigatório")
    private String local;

    public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.titulo = evento.getTitulo();
        this.descricao = evento.getDescricao();
        this.dataEvento = evento.getDataEvento();
        this.local = evento.getLocal();
    }

}
