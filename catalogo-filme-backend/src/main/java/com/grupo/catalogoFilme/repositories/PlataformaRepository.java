package com.grupo.catalogoFilme.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.enums.StatusRegistro;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Integer>{

	List<Plataforma> findAllByStatusNot(StatusRegistro status);

	@Modifying
	@Transactional
	@Query("UPDATE Plataforma p SET p.status = com.grupo.catalogoFilme.enums.StatusRegistro.INATIVO WHERE p.id = :id")
	int logicalDeleteById(@Param("id") Integer id);

}
