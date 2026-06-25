package com.grupo.catalogoFilme.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo.catalogoFilme.entities.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
	Optional<Cargo> findByNome(String nome);
}
