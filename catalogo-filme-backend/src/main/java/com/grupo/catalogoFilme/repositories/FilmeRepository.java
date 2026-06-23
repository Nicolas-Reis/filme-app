package com.grupo.catalogoFilme.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo.catalogoFilme.entities.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{

}
