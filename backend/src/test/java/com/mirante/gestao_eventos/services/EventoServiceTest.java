package com.mirante.gestao_eventos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mirante.gestao_eventos.dtos.EventoDTO;
import com.mirante.gestao_eventos.repositorys.IEventoRepository;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    IEventoRepository repository;

    @InjectMocks
    EventoService service;

    @Test
    void buscarTodos_returnsPageOfEventoDTOs() {
        int page = 0;
        int count = 2;
        Pageable pageable = PageRequest.of(page, count);

        EventoDTO dto1 = new EventoDTO();
        EventoDTO dto2 = new EventoDTO();
        Page<EventoDTO> expectedPage = new PageImpl<>(List.of(dto1, dto2), pageable, 2);

        when(repository.buscarTodosAtivos(any())).thenReturn(expectedPage);

        Page<EventoDTO> result = service.buscarTodos(page, count);

        assertEquals(2, result.getTotalElements());
        assertEquals(dto1, result.getContent().get(0));
        assertEquals(dto2, result.getContent().get(1));
        verify(repository).buscarTodosAtivos(pageable);
    }

    @Test
    void buscarTodos_emptyPage() {
        int page = 1;
        int count = 5;
        Pageable pageable = PageRequest.of(page, count);

        Page<EventoDTO> expectedPage = new PageImpl<>(List.of(), pageable, 0);

        when(repository.buscarTodosAtivos(any())).thenReturn(expectedPage);

        Page<EventoDTO> result = service.buscarTodos(page, count);

        assertTrue(result.isEmpty());
        verify(repository).buscarTodosAtivos(pageable);
    }
}