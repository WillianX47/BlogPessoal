package org.generation.blogPessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/postagem")
@Api(tags = "Controlador de Postagem", description = "Utilitário de postagem")
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	@ApiOperation(value = "Procura todas as postagens cadastradas no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Postagens encontradas"),
			@ApiResponse(code = 204, message = "Não existe postagens no sistema") })
	@GetMapping
	public ResponseEntity<List<Postagem>> findAllPostagens() {
		List<Postagem> objetoLista = repository.findAll();
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.ok(objetoLista);
		}
	}

	@ApiOperation(value = "Procura uma postagem por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Postagem encontrada"),
			@ApiResponse(code = 400, message = "Não existe postagem com esse id") })
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> findAllById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura todas as postagens cadastradas no sistema pelo texto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Postagens encontradas"),
			@ApiResponse(code = 204, message = "Não existe postagens no sistema com esse texto") })
	@GetMapping("/texto/{texto}")
	public ResponseEntity<List<Postagem>> findAllByTexto(@PathVariable String texto) {
		List<Postagem> objetoLista = repository.findAllByTextoPostagemContainingIgnoreCase(texto);
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.ok(objetoLista);
		}
	}

	@ApiOperation(value = "Procura todas as postagens cadastradas no sistema pelo titulo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Postagens encontradas"),
			@ApiResponse(code = 204, message = "Não existe postagens no sistema com esse titulo") })
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> findByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloPostagemContainingIgnoreCase(titulo));
	}

	@ApiOperation(value = "Salva uma postagem no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Postagem salva"),
			@ApiResponse(code = 400, message = "Erro no body solicitado") })
	@PostMapping("/novaPostagem")
	public ResponseEntity<Postagem> novaPostagem(@RequestBody Postagem novaPostagem) {
		return ResponseEntity.status(201).body(repository.save(novaPostagem));
	}

	@ApiOperation(value = "Salva uma postagem no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Postagem salva"),
			@ApiResponse(code = 400, message = "Erro no body solicitado") })
	@PutMapping("/atualizarPostagem")
	public ResponseEntity<Postagem> atualizarPostagem(@Valid @RequestBody Postagem postagemAtualizada) {
		return repository.findById(postagemAtualizada.getId()).map(resp -> {
			resp.setTituloPostagem(postagemAtualizada.getTituloPostagem());
			resp.setTextoPostagem(postagemAtualizada.getTextoPostagem());
			resp.setDataPostagem(postagemAtualizada.getDataPostagem());
			return ResponseEntity.ok(repository.save(resp));
		}).orElseGet(() -> {
			return ResponseEntity.status(400).build();
		});
	}

	@ApiOperation(value = "Deleta uma postagem no sistema pelo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Postagem deletada"),
			@ApiResponse(code = 400, message = "Não existe postagem com esse id") })
	@DeleteMapping("/deletarPostagem/{id}")
	public ResponseEntity<Object> deletarPostagem(@PathVariable(value = "id") Long id) {
		return repository.findById(id).map(resp -> {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		}).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Digite um id válido");
		});
	}
}
