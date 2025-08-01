package com.mirante.gestao_eventos.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirante.gestao_eventos.dtos.EventoDTO;
import com.mirante.gestao_eventos.models.Evento;

public interface IEventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT new com.mirante.dtos.EventoDTO(e.id, e.titulo, e.descricao, e.dataEvento, e.local) FROM Evento e WHERE e.ativo = true AND e.id = :id")
    public EventoDTO buscarPorId(Long id);

    @Query("SELECT new com.mirante.dtos.EventoDTO(e.id, e.titulo, e.descricao, e.dataEvento, e.local) FROM Evento e WHERE e.ativo = true AND e.id = :id")
    public Page<EventoDTO> buscarTodosAtivos(Pageable page);

}
