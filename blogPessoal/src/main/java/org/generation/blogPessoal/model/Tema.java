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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tb_tema")
public class Tema {

	/**
	 * Id de tema
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	private @NotNull String descricaoTema;

	@JsonIgnoreProperties("temaPostagem")
	@OneToMany(mappedBy = "temaPostagem", cascade = CascadeType.REMOVE)
	@ApiModelProperty(hidden = true)
	private List<Postagem> postagem = new ArrayList<>();
	
	public String getDescricaoTema() {
		return descricaoTema;
	}

	public void setDescricaoTema(String descricaoTema) {
		this.descricaoTema = descricaoTema;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

}
