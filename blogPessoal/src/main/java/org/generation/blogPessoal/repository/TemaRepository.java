package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

	/**
	 * Procura todas as postagens encontradas pela descrição
	 * @param descricaoTema
	 * @return lista de postagens encontradas pela descrição
	 * @author Will
	 */
	public List<Tema> findAllByDescricaoTemaContainingIgnoreCase(String descricaoTema);
	
	/**
	 * Procura todos os temas pela descrição
	 * @param descricaoTema
	 * @return Objeto Optional de postagem encontado pela descrição
	 * @author Will
	 */
	public Optional<Tema> findByDescricaoTemaContainingIgnoreCase(String descricaoTema);
}
