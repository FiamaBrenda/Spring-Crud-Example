package com.login.exemplo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.exemplo.entity.Produto;
import com.login.exemplo.repositories.ProdutoRepository;

@RestController
@RequestMapping("produto")
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepository;
	
	// Buscar por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarId(@PathVariable int id){
		Optional<Produto> produto = produtoRepository.findById(id);		
		if (produto.isPresent()) {
			return ResponseEntity.ok(produto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não Encontrado");
		}	
	}
	
	// Busca todos
	@GetMapping
	public ResponseEntity<?> buscarTodos(){
		 List<Produto> lista = produtoRepository.findAll();
		return ResponseEntity.ok(lista);
	}
	
	// Criar produto
	@PostMapping
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto){
		Produto novo = new Produto(
				produto.getNome(), produto.getPreco(), produto.getQuantidade());
		produtoRepository.save(novo);
		return ResponseEntity.ok(novo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarId(@PathVariable int id){
		boolean produto = produtoRepository.existsById(id);		
		if (produto) {
			produtoRepository.deleteById(id);
			return ResponseEntity.ok("Deletado com Sucesso!!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não Encontrado");
		}	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> alterarProduto(@PathVariable int id, 
			@RequestBody Produto produto){
		Optional<Produto> novo = produtoRepository.findById(id);
		
		if (novo.isPresent()) {
			Produto e = novo.get();
			e.setQuantidade(produto.getQuantidade());
			produtoRepository.save(e);
			return ResponseEntity.ok(novo);
		} else {
			// return ResponseEntity.notFound().build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Esse ID não existe");
		}
	}
	
}
