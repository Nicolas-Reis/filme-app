package com.grupo.catalogoFilme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo.catalogoFilme.entities.Plataforma;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long>{

}
