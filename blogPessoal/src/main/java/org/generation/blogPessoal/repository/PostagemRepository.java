package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {
	
	/**
	 * Procura todos os titulos ignorando case
	 * @param titulo
	 * @return
	 */
	public List<Postagem> findAllByTituloPostagemContainingIgnoreCase(String tituloPostagem);

	/**
	 * Procura todos os id
	 * @param id
	 * @return
	 */
	public Optional<Postagem> findAllById(Long id);
	
	public List<Postagem> findAllByTextoPostagemContainingIgnoreCase(String textoPostagem);
	
}
