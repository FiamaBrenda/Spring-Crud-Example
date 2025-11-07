package com.login.exemplo.dto;

import com.login.exemplo.entity.Produto;

public class ProdutoResponseDTO {
	private String nome;
	private double preco;
	private int quantidade;
	private double subTotal;
	
	public ProdutoResponseDTO(Produto prod) {
		this.nome = prod.getNome();
		this.preco = prod.getPreco();
		this.quantidade = prod.getQuantidade();
		this.subTotal = getSubTotal();
	}

	public String getNome() {
		return nome;
	}

	public double getPreco() {
		return preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public double getSubTotal() {
		subTotal = preco * quantidade;
		return subTotal;
	}

}
