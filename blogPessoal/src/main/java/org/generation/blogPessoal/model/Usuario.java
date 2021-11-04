package org.generation.blogPessoal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

/**
 * Cria uma tabela no banco de dados com o nome "tb_usuario"
 * 
 * @author Will
 */
@Entity
@Table(name = "tb_usuario")
public class Usuario {

	// Gera o id automaticamente
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	// Cria um atributo de nome
	private @NotBlank @Size(min = 2, max = 100) String nome;

	// Cria um atributo de senha
	private @NotBlank @Size(min = 5, max = 100) String senha;

	// Cria um atributo do tipo foto
	private String foto;
	
	//Cria um atributo do tipo tipo
	private String tipo;
	
	// Cria um atributo de usuario
	@ApiModelProperty(example = "email@email.com.br")
	@NotBlank(message = "O atributo usuario é obrigatório!")
	@Email(message = "O atributo usuario deve ser um email valido!")
	private @Size(min = 5, max = 100) String usuario;

	// Link tabela OneToMany para a tabela de postagem
	@OneToMany(mappedBy = "criador", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "criador" })
	@ApiModelProperty(hidden = true)
	private List<Postagem> minhasPostagens = new ArrayList<>();
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Postagem> getMinhasPostagens() {
		return minhasPostagens;
	}

	public void setMinhasPostagens(List<Postagem> minhasPostagens) {
		this.minhasPostagens = minhasPostagens;
	}

}
