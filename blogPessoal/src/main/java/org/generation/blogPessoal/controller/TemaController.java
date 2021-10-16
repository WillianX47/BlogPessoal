package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.TemaRepository;
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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/tema")
public class TemaController {
	@Autowired
	private TemaRepository repository;

	@ApiOperation(value = "Procura todos os temas cadastrados no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Temas encontrados"),
			@ApiResponse(code = 204, message = "Não existe temas no sistema") })
	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		List<Tema> objetoLista = repository.findAll();
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.ok(objetoLista);
		}
	}

	@ApiOperation(value = "Procura um tema pelo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tema encontrado"),
			@ApiResponse(code = 404, message = "Não existe tema com esse id") })
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura todos os temas cadastrados com o nome solicitado via URL")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Temas encontrados"),
			@ApiResponse(code = 204, message = "Não existe temas no sistema com este nome") })
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> getByName(@PathVariable String descricaoTema) {
		List<Tema> objetoLista = repository.findAllByDescricaoTemaContainingIgnoreCase(descricaoTema);
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.ok(objetoLista);
		}
	}

	@ApiOperation(value = "Cadastra um tema novo no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Temas encontrados"),
			@ApiResponse(code = 400, message = "Já existe tema com este nome no sistema") })
	@PostMapping("/criar")
	public ResponseEntity<Object> post(@RequestBody Tema tema) {
		return repository.findByDescricaoTemaContainingIgnoreCase(tema.getDescricaoTema()).map(resp -> {
			return ResponseEntity.status(400).build();
		}).orElseGet(() -> {
			return ResponseEntity.ok(repository.save(tema));
		});
	}

	@ApiOperation(value = "Atualiza um tema no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tema atualizado"),
			@ApiResponse(code = 400, message = "Não existe um tema com esse id") })
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema, @PathVariable Long id) {
		return repository.findById(id).map(resp -> {
			resp.setDescricaoTema(tema.getDescricaoTema());
			return ResponseEntity.ok(repository.save(resp));
		}).orElseGet(() -> {
			return ResponseEntity.status(400).build();
		});
	}

	@ApiOperation(value = "Deletar um tema no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tema deletado"),
			@ApiResponse(code = 400, message = "Não existe um tema com esse id") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Tema> delete(@PathVariable Long id) {
		Optional<Tema> objetoOptional = repository.findById(id);
		if(objetoOptional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(400).build();
		}
	}
}
