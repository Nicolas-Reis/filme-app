package com.grupo.catalogoFilme.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.enums.StatusRegistro;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer>{

	List<Filme> findAllByStatusNot(StatusRegistro status);

	@Modifying
	@Transactional
	@Query("UPDATE Filme f SET f.status = com.grupo.catalogoFilme.enums.StatusRegistro.INATIVO WHERE f.id = :id")
	int logicalDeleteById(@Param("id") Integer id);

}
