package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/v1/usuario")
@Api(tags = "Controlador de usuario", description = "Utilitário de usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@ApiOperation(value = "Procura todos os usuários cadastrados no sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Usuários encontrados"),
			@ApiResponse(code = 204, message = "Não existe usuários no sistema") })
	@GetMapping
	public ResponseEntity<List<Usuario>> todosUsuarios() {
		return usuarioService.mostrarTodos();
	}
	
	@ApiOperation(value = "Procura um usuário por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Usuário encontrado"),
			@ApiResponse(code = 404, message = "Não existe usuário com este id no sistema") })
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findByIdUsuario(@PathVariable Long id){
		return usuarioService.findByIdUsuario(id);
	}
	
	@ApiOperation(value = "Atualiza um usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Usuário atualizado"),
			@ApiResponse(code = 400, message = "Usuário invalido") })
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> attUsuario(@RequestBody Usuario usuario){
		return usuarioService.atualizarCadastro(usuario);
	}
 
	@ApiOperation(value = "Executa o login de usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login efetuado"),
			@ApiResponse(code = 401, message = "Usuario ou senha incorreto") })
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user) {
		return usuarioService.logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@ApiOperation(value = "Realiza o cadastro de usuario")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastro efetuado"),
			@ApiResponse(code = 400, message = "Usuário já existe no sistema") })
	@PostMapping("/cadastrar")
	public ResponseEntity<Object> Post(@RequestBody Usuario usuario) {
		return usuarioService.cadastrarUsuario(usuario).map(resp -> ResponseEntity.status(201).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe");
				});
	}

}
