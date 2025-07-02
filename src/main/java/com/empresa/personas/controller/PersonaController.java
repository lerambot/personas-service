package com.empresa.personas.controller;

import com.empresa.personas.model.Persona;
import com.empresa.personas.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
@Tag(name = "Personas", description = "API para gestión de personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Operation(summary = "Crear una nueva persona", description = "Crea y guarda una nueva persona en la base de datos")
    @PostMapping
    public ResponseEntity<Persona> crearPersona(
            @Parameter(description = "Objeto persona a crear", required = true)
            @RequestBody Persona persona) {
        Persona newPersona = personaService.crearPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPersona);
    }

    @Operation(summary = "Obtener todas las personas", description = "Retorna la lista completa de personas registradas")
    @GetMapping
    public List<Persona> obtenerTodas() {
        return personaService.obtenerTodas();
    }

    @Operation(summary = "Obtener persona por ID", description = "Retorna una persona dado su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPorId(
            @Parameter(description = "ID de la persona a buscar", required = true)
            @PathVariable Long id) {
        Optional<Persona> persona = personaService.obtenerPorId(id);
        return persona.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Actualizar persona", description = "Actualiza los datos de una persona existente dado su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(
            @Parameter(description = "ID de la persona a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la persona", required = true)
            @RequestBody Persona personaDetails) {
        try {
            Persona updatedPersona = personaService.actualizarPersona(id, personaDetails);
            return ResponseEntity.ok(updatedPersona);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Eliminar persona", description = "Elimina una persona dado su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(
            @Parameter(description = "ID de la persona a eliminar", required = true)
            @PathVariable Long id) {
        try {
            personaService.eliminarPersona(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
