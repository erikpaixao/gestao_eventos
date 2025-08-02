package com.mirante.gestao_eventos.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mirante.gestao_eventos.dtos.EventoDTO;
import com.mirante.gestao_eventos.models.Evento;
import com.mirante.gestao_eventos.repositorys.IEventoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventoService {

    private final IEventoRepository repository;

    public EventoService(IEventoRepository repository) {
        this.repository = repository;
    }

    public EventoDTO buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public Page<EventoDTO> buscarTodos(int page, int count) {

        Pageable pageable = PageRequest.of(page, count);

        return repository.buscarTodosAtivos(pageable);

    }

    public EventoDTO salvar(EventoDTO eventoDTO) {

        Evento evento = new Evento(eventoDTO);

        repository.save(evento);

        return new EventoDTO(evento);
    }

    public EventoDTO atualizar(Long id, EventoDTO eventoDTO) {

        Evento evento = new Evento(eventoDTO);

        evento.setId(id);

        repository.save(evento);

        return new EventoDTO(evento);
    }

    public void remover(Long id) {

        Optional<Evento> eventoOptional = repository.findById(id);

        if (eventoOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Evento evento = eventoOptional.get();
        evento.setAtivo(false);
        repository.save(evento);

    }

}
