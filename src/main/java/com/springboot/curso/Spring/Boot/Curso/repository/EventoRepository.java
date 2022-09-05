package com.springboot.curso.Spring.Boot.Curso.repository;

import com.springboot.curso.Spring.Boot.Curso.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String> {

    Evento findByCodigo(long codigo);
}
