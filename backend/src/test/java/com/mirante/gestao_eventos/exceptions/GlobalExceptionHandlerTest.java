package com.mirante.gestao_eventos.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @SuppressWarnings("deprecation")
    @Test
    void handleValidationExceptions_returnsBadRequestWithErrorMessages() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error1 = new FieldError("object", "field1", "Field1 is invalid");
        FieldError error2 = new FieldError("object", "field2", "Field2 is required");
        List<FieldError> errors = List.of(error1, error2);

        when(bindingResult.getFieldErrors()).thenReturn(errors);

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().get("erro").contains("Field1 is invalid"));
        assertTrue(response.getBody().get("erro").contains("Field2 is required"));
        assertTrue(response.getBody().get("erro").endsWith("; "));
    }

    @SuppressWarnings("deprecation")
    @Test
    void handleValidationExceptions_withNoErrors_returnsBadRequestWithEmptyMessage() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("", response.getBody().get("erro"));
    }
}