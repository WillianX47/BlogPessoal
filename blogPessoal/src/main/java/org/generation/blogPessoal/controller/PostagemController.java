package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/postagem")
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepositorio;

	/**
	 * Retorna a lista inteira, senão, retorna status 204
	 * 
	 * @author Will
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Postagem>> findAllPostagens() {
		List<Postagem> objetoLista = postagemRepositorio.findAll();
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.ok(objetoLista);
		}
	}

	/**
	 * Retorna o id procurado pelo usuário
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Postagem> findAllById(@PathVariable Long id) {
		return postagemRepositorio.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * Procura de acordo com o texto
	 * @param texto
	 * @return
	 */
	@GetMapping("/texto/{texto}")
	public ResponseEntity <List<Postagem>> findAllByTexto(@PathVariable String texto){
		return ResponseEntity.ok(postagemRepositorio.findAllByTextoPostagemContainingIgnoreCase(texto));
	}

	/**
	 * Retorna o titulo procurado pelo usuário
	 * 
	 * @param titulo
	 * @return
	 */
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> findByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepositorio.findAllByTituloPostagemContainingIgnoreCase(titulo));
	}
	
	/**
	 * Cria uma nova postagem
	 * @param novaPostagem
	 * @return
	 */
	@PostMapping("/novaPostagem")
	public ResponseEntity<Postagem> novaPostagem(@RequestBody Postagem novaPostagem){
		return ResponseEntity.status(201).body(postagemRepositorio.save(novaPostagem));
	}
	
	/**
	 * Atualiza uma postagem
	 * @param postagemAtualizada
	 * @return
	 */
	@PutMapping("/atualizarPostagem")
	public ResponseEntity<Postagem> atualizarPostagem(@Valid @RequestBody Postagem postagemAtualizada){
		return ResponseEntity.status(201).body(postagemRepositorio.save(postagemAtualizada));
	}
	
	/**
	 * Deleta uma postagem por ID
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deletarPostagem/{id}")
	public ResponseEntity<Postagem> deletarPostagem(@PathVariable(value = "id") Long id){
		Optional<Postagem> postagemObj = postagemRepositorio.findById(id);
		if(postagemObj.isPresent()) {
			postagemRepositorio.deleteById(id);
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(404).build();
		}
	}

}
