package com.tequila.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tequila.brewer.model.Usuario;
import com.tequila.brewer.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

	Optional<Usuario> findByEmailOrCodigo(String email, Long codigo);

	public List<Usuario> findByCodigoIn(Long[] codigos);
	
}
