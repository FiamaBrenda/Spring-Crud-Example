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

import com.login.exemplo.dto.ProdutoResponseDTO;
import com.login.exemplo.entity.Produto;
import com.login.exemplo.repositories.ProdutoRepository;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepository;

	// Buscar por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarId(@PathVariable int id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			ProdutoResponseDTO prod = new ProdutoResponseDTO(produto.get());
			return ResponseEntity.ok(prod);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não Encontrado");
		}
	}

	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> buscarTodos() {
		List<Produto> lista = produtoRepository.findAll();
		List<ProdutoResponseDTO> listaDTO = lista.stream().map(ProdutoResponseDTO::new).toList();
		return ResponseEntity.ok(listaDTO);
	}

	// Criar produto
	@PostMapping
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoRequestDTO dto) {
		Produto novo = new Produto(dto.getNome(), dto.getPreco(), dto.getQuantidade());
		produtoRepository.save(novo);
		return ResponseEntity.ok(new ProdutoResponseDTO(novo));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarId(@PathVariable int id) {
		boolean produto = produtoRepository.existsById(id);
		if (produto) {
			produtoRepository.deleteById(id);
			return ResponseEntity.ok("Deletado com Sucesso!!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não Encontrado");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterarProduto(@PathVariable int id, @RequestBody ProdutoRequestDTO  produto) {
		Optional<Produto> produtoExistente = produtoRepository.findById(id);

		if (produtoExistente.isPresent()) {
			Produto produtoAlterado = produtoExistente.get();
			produtoAlterado.setQuantidade(produto.getQuantidade());
			produtoRepository.save(produtoAlterado);
			return ResponseEntity.ok(new ProdutoResponseDTO(produtoAlterado));
		} else {
			// return ResponseEntity.notFound().build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse ID não existe");
		}
	}

}
