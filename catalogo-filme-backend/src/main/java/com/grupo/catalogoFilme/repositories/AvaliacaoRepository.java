package com.grupo.catalogoFilme.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grupo.catalogoFilme.entities.Avaliacao;
import com.grupo.catalogoFilme.enums.StatusRegistro;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer>{

	List<Avaliacao> findAllByStatusNot(StatusRegistro status);

	@Modifying
	@Transactional
	@Query("UPDATE Avaliacao a SET a.status = com.grupo.catalogoFilme.enums.StatusRegistro.INATIVO WHERE a.id = :id")
	int logicalDeleteById(@Param("id") Integer id);

}
