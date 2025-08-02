package com.mirante.gestao_eventos.controllers;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirante.gestao_eventos.models.Evento;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long eventoId;

    @Test
    @Order(1)
    void deveIncluirEvento() throws Exception {
        Evento evento = new Evento();
        evento.setTitulo("Evento Teste");
        evento.setDescricao("Descrição do evento teste");
        evento.setDataEvento(LocalDateTime.now().plusDays(1));
        evento.setLocal("Local do Evento Teste");

        String response = mockMvc.perform(post("/api/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Evento Teste"))
                .andReturn().getResponse().getContentAsString();

        Evento eventoCriado = objectMapper.readValue(response, Evento.class);
        eventoId = eventoCriado.getId();
    }

    @Test
    @Order(2)
    void deveListarEventos() throws Exception {
        mockMvc.perform(get("/api/eventos?page=0&count=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].id", hasItem(eventoId.intValue())))
                .andExpect(jsonPath("$.content[*].titulo", hasItem("Evento Teste")));
    }

    @Test
    @Order(3)
    void deveAtualizarEvento() throws Exception {
        Evento eventoAtualizado = new Evento();
        eventoAtualizado.setTitulo("Evento Atualizado");
        eventoAtualizado.setDescricao("Descrição atualizada");
        eventoAtualizado.setDataEvento(LocalDateTime.now().plusDays(1));
        eventoAtualizado.setLocal("Local do Evento Teste");

        mockMvc.perform(put("/api/eventos/{id}", eventoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Evento Atualizado"))
                .andExpect(jsonPath("$.descricao").value("Descrição atualizada"));
    }

    @Test
    @Order(4)
    void deveRemoverEvento() throws Exception {
        mockMvc.perform(delete("/api/eventos/{id}", eventoId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/eventos/{id}", eventoId))
                .andExpect(status().isOk());
    }
}
