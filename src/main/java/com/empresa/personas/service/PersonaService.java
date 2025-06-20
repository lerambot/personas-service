package com.empresa.personas.service;

import com.empresa.personas.model.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {

    Persona crearPersona(Persona persona);

    List<Persona> obtenerTodas();

    Optional<Persona> obtenerPorId(Long id);

    Persona actualizarPersona(Long id, Persona persona);

    void eliminarPersona(Long id);
}