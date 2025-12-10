package edu.dev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(sequenceName = "seq_produto", name = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;

	private String nome;

	private Double preco;

	private Integer quantidade;

	public Produto(String nome) {
		super();
		this.nome = nome;
	}

	public Produto() {
	}

}
