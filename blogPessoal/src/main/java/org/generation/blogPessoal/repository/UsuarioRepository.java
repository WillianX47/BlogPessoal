package org.generation.blogPessoal.repository;

import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	/**
	 * Utilizado para pesquisar uma postagem por usuario
	 * @param Usuario
	 * @return Objeto Optional com o atributo Usuario
	 * @author Will
	 */
	public Optional<Usuario> findByUsuario(String Usuario);
	
}
