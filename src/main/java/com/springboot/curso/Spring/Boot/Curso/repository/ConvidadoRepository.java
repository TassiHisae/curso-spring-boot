package com.springboot.curso.Spring.Boot.Curso.repository;

import com.springboot.curso.Spring.Boot.Curso.models.Convidado;
import com.springboot.curso.Spring.Boot.Curso.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {

    Iterable<Convidado> findByEvento(Evento evento);
}
