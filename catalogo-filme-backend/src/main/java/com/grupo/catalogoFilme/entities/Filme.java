package com.grupo.catalogoFilme.entities;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.grupo.catalogoFilme.enums.GeneroEnum;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.enums.converter.GeneroEnumConverter;
import com.grupo.catalogoFilme.enums.converter.StatusRegistroConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "filmes")
public class Filme {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@Column(name = "diretor")
	private String diretor;

	@Column(name = "data_lancamento")
	private LocalDate dataLancamento;

	@Column(name = "url_image")
	private String urlImage;

	@Convert(converter = StatusRegistroConverter.class)
	@Column(name = "status", nullable = false)
	private StatusRegistro status = StatusRegistro.ATIVO;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "filme_genero", joinColumns = @JoinColumn(name = "filme_id"))
	@Column(name = "genero", nullable = false)
	@Convert(converter = GeneroEnumConverter.class)
	private Set<GeneroEnum> generos = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "plataforma_id")
	@JsonManagedReference
	private Plataforma plataforma;
	
	@OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Avaliacao> avaliacoes;

	public Filme() {}

	public Filme(Integer id, String titulo, String descricao, String diretor, LocalDate dataLancamento, String urlImage, Set<GeneroEnum> generos, Plataforma plataforma) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.diretor = diretor;
		this.dataLancamento = dataLancamento;
		this.urlImage = urlImage;
		this.generos = generos;
		this.plataforma = plataforma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}

	public Set<GeneroEnum> getGeneros() {
		return generos;
	}

	public void setGeneros(Set<GeneroEnum> generos) {
		this.generos = generos;
	}

	public Plataforma getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Filme filme = (Filme) o;
		return id != null && id.equals(filme.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}

	