package com.mirante.gestao_eventos.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mirante.gestao_eventos.dtos.EventoDTO;
import com.mirante.gestao_eventos.models.Evento;

public interface IEventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT new com.mirante.gestao_eventos.dtos.EventoDTO(e.id, e.titulo, e.descricao, e.dataEvento, e.local) FROM Evento e WHERE e.ativo = true AND e.id = :id")
    public EventoDTO buscarPorId(@Param("id") Long id);

    @Query("SELECT new com.mirante.gestao_eventos.dtos.EventoDTO(e.id, e.titulo, e.descricao, e.dataEvento, e.local) FROM Evento e WHERE e.ativo = true")
    public Page<EventoDTO> buscarTodosAtivos(Pageable page);

}
