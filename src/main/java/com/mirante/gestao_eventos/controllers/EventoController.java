package com.mirante.gestao_eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirante.gestao_eventos.dtos.EventoDTO;
import com.mirante.gestao_eventos.services.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventoController {

    @Autowired
    EventoService service;

    @GetMapping
    public ResponseEntity<Page<EventoDTO>> getAll(
            @RequestParam("page") int page,
            @RequestParam("count") int count) {
        return ResponseEntity.ok(service.buscarTodos(page, count));
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<EventoDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EventoDTO> save(@Valid EventoDTO evento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(evento));
    }

    @PutMapping
    @RequestMapping("/{id}")
    public ResponseEntity<EventoDTO> update(@PathVariable("id") Long id, @Valid EventoDTO evento) {
        return ResponseEntity.ok(service.atualizar(id, evento));
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity<EventoDTO> delete(@PathVariable("id") Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
