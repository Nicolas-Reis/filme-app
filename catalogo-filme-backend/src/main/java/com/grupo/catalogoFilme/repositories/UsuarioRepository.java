package com.grupo.catalogoFilme.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.enums.StatusRegistro;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	Optional<Usuario> findByEmail(String email);

	List<Usuario> findAllByStatusNot(StatusRegistro status);

	@Modifying
	@Transactional
	@Query("UPDATE Usuario u SET u.status = com.grupo.catalogoFilme.enums.StatusRegistro.INATIVO WHERE u.id = :id")
	int logicalDeleteById(@Param("id") Integer id);

}
