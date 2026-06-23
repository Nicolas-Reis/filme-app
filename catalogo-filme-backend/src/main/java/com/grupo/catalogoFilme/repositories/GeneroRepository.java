package com.grupo.catalogoFilme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo.catalogoFilme.entities.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long>{

}
