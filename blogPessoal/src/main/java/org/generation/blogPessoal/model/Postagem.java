package org.generation.blogPessoal.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cria uma tabela no banco de dados com o nome "tb_postagem"
 * 
 * @author Will
 *
 */

@Entity
@Table(name = "tb_postagem")
public class Postagem {

	// Gera o id automaticamente
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	// Cria um atributo de tituloPostagem
	private @NotBlank @Size(min = 5, max = 100) String tituloPostagem;

	// Cria um atributo de textoPostagem
	private @NotBlank @Size(min = 5, max = 500) String textoPostagem;

	/**
	 * Cria um atributo de dataPostagem com o horário atual do computador, utiliza o
	 * pattern indicado abaixo
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPostagem = LocalDate.now();

	// Link tabela ManyToOne para a tabela de tb_tema
	@ManyToOne
	@JoinColumn(name = "tema_id")
	@JsonIgnoreProperties({ "postagem" })
	private Tema temaPostagem;

	// Link tabela OneToMany para a tabela de tb_usuario
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

	public LocalDate getDataPostagem() {
		return dataPostagem;
	}

	public void setDataPostagem(LocalDate dataPostagem) {
		this.dataPostagem = dataPostagem;
	}

}
