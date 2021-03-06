package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	/**
	 * Método utilizado para criptografar a senha
	 * 
	 * @return String
	 * @author Will
	 */
	public String bcrypt(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	/**
	 * Método utilizado para gerar o token do usuário autenticado com sucesso
	 * 
	 * @return String
	 * @author Will
	 */
	public String gerarToken(String email, String senha) {
		String auth = email + ":" + senha;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(encodedAuth);
	}

	/**
	 * Método utilizado para mostrar todos os usuários cadastrados no sistema
	 * 
	 * @return Body lista de usuarios
	 * @author Will
	 */
	public ResponseEntity<List<Usuario>> mostrarTodos() {
		List<Usuario> objetoLista = repository.findAll();
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.ok(objetoLista);
		}
	}

	/**
	 * Método utilizado para atualizar um cadastro
	 * 
	 * @return Ok
	 * @author Will
	 */
	public ResponseEntity<Usuario> atualizarCadastro(Usuario usuario) {
		return repository.findById(usuario.getId()).map(resp -> {
			usuario.setSenha(bcrypt(usuario.getSenha()));
			resp.setNome(usuario.getNome());
			resp.setUsuario(usuario.getUsuario());
			resp.setSenha(usuario.getSenha());
			resp.setFoto(usuario.getFoto());
			resp.setTipo(usuario.getTipo());
			return ResponseEntity.ok(repository.save(resp));
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().build();
		});
	}

	/**
	 * Método utilizado para cadastrar um usuário no sistema, verifica se o usuário
	 * já existe
	 * 
	 * @param usuario
	 * @return salva usuário no repositório e retorna um Optional
	 * @author Will
	 */
	public Optional<Object> cadastrarUsuario(Usuario usuario) {
		return repository.findByUsuario(usuario.getUsuario()).map(resp -> {
			return Optional.empty();
		}).orElseGet(() -> {
			usuario.setSenha(bcrypt(usuario.getSenha()));
			return Optional.ofNullable(repository.save(usuario));
		});

	}

	/**
	 * Método utilizado para encontrar um usuário pelo id no sistema
	 * 
	 * @param id
	 * @return Usuário
	 * @author Will
	 */
	public ResponseEntity<Usuario> findByIdUsuario(Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Utilizado para logar um usuário no sistema
	 * 
	 * @param user
	 * @return retorna um UserLogin
	 * @author Will
	 */
	public Optional<UserLogin> logar(Optional<UserLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());
		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				user.get().setToken(gerarToken(user.get().getUsuario(), user.get().getSenha()));
				user.get().setUsuario(usuario.get().getUsuario());
				user.get().setId(usuario.get().getId());
				user.get().setNome(usuario.get().getNome());
				user.get().setSenha(usuario.get().getSenha());
				user.get().setFoto(usuario.get().getFoto());
				user.get().setTipo(usuario.get().getTipo());
				return user;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta!");
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email incorreto!");
	}
}
