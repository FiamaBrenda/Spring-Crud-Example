package com.login.exemplo.controllers;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoRequestDTO {
	
	@NotBlank
	private String nome;

	@NotNull @Min(0)
	private Double preco;

	@NotNull @Min(0)
	private int quantidade;
	
	public ProdutoRequestDTO() {
		
	}

	public ProdutoRequestDTO(String nome, Double preco, int quantidade) {
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
