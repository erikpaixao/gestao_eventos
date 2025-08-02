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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirante.gestao_eventos.dtos.EventoDTO;
import com.mirante.gestao_eventos.services.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/eventos")
@Tag(name = "Eventos", description = "Gerenciamento de eventos")
public class EventoController {

    @Autowired
    EventoService service;

    @Operation(summary = "Listar todos os eventos paginados")
    @GetMapping
    public ResponseEntity<Page<EventoDTO>> getAll(
            @Parameter(description = "Número da página (iniciando em 0)") @RequestParam("page") int page,
            @Parameter(description = "Quantidade de registros por página") @RequestParam("count") int count) {
        return ResponseEntity.ok(service.buscarTodos(page, count));
    }

    @Operation(summary = "Buscar um evento pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> get(
            @Parameter(description = "ID do evento") @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Criar um novo evento")
    @PostMapping
    public ResponseEntity<EventoDTO> save(@Valid @RequestBody EventoDTO evento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(evento));
    }

    @Operation(summary = "Atualizar um evento existente")
    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> update(
            @Parameter(description = "ID do evento") @PathVariable("id") Long id,
            @Valid @RequestBody EventoDTO evento) {
        return ResponseEntity.ok(service.atualizar(id, evento));
    }

    @Operation(summary = "Remover um evento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do evento") @PathVariable("id") Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
