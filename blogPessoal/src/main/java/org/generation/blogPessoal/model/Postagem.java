package org.generation.blogPessoal.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe espelho tabela Postagem no db_blogpessoal
 * 
 * @author Will
 *
 */

@Entity
@Table(name = "tb_postagem")
public class Postagem {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	private @NotBlank @Size(min = 5, max = 100) String tituloPostagem;

	private @NotBlank @Size(min = 5, max = 500) String textoPostagem;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPostagem = new java.sql.Date(System.currentTimeMillis());

	@ManyToOne
	@JoinColumn(name = "tema_id")
	@JsonIgnoreProperties({ "postagem" })
	private Tema temaPostagem;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties({ "minhasPostagens" })
	private Usuario criador;

	public Usuario getCriador() {
		return criador;
	}

	public void setCriador(Usuario criador) {
		this.criador = criador;
	}

	public Tema getTemaPostagem() {
		return temaPostagem;
	}

	public void setTemaPostagem(Tema temaPostagem) {
		this.temaPostagem = temaPostagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTituloPostagem() {
		return tituloPostagem;
	}

	public void setTituloPostagem(String tituloPostagem) {
		this.tituloPostagem = tituloPostagem;
	}

	public String getTextoPostagem() {
		return textoPostagem;
	}

	public void setTextoPostagem(String textoPostagem) {
		this.textoPostagem = textoPostagem;
	}

	public Date getDataPostagem() {
		return dataPostagem;
	}

	public void setDataPostagem(Date dataPostagem) {
		this.dataPostagem = dataPostagem;
	}

}
