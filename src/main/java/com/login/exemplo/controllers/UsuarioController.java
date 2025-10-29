package com.login.exemplo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.exemplo.entity.Usuario;
import com.login.exemplo.repositories.UsuarioRepository;

@RestController
@RequestMapping("usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@PostMapping(value = "cadastro")
	public ResponseEntity<?> saveUser(@RequestBody Usuario user) {
		Usuario usuario = new Usuario(user.getName(), user.getEmail(), user.getPassword());
		usuarioRepository.save(usuario);
		return ResponseEntity.ok("Usuario salvo com sucesso" + usuario);
	}

	@PostMapping(value = "login")
	public ResponseEntity<?> login(@RequestBody Usuario user) {
		Usuario findUser = usuarioRepository.findByEmail(user.getEmail());
		if (findUser == null) {
			return ResponseEntity.ok("Usuario não encontrado");
		} else {
			if (findUser.getPassword().equals(user.getPassword())) {
				return ResponseEntity.ok("Logado com sucesso!!");
			} else {
				return ResponseEntity.ok("Senha incorreta");
			}

		}

	}

	@GetMapping(value = "listar/Fiama")
	public List<Usuario> listarUsuarios1() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAll();
		return listaDeUsuarios;
	}

	@GetMapping(value = "listar/Vitinho")
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	// return ResponseEntity.status(HttpStatus.CREATED).body(dto);

	@GetMapping(value = "{id}")
	public Optional<Usuario> usuarioPorId(@PathVariable int id) {
		return usuarioRepository.findById(id);
	}

	// import org.springframework.http.HttpStatus;

	@DeleteMapping("apagar/{id}")
	public ResponseEntity<?> deletar(@PathVariable int id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Excluido com Sucesso!"); // 204
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse ID não existe"); // 404
		}
	}

	@PutMapping("atualizar/{id}")
	public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody Usuario novoUsuario) {
		Optional<Usuario> UsuarioExistente = usuarioRepository.findById(id);

		if (UsuarioExistente.isPresent()) {
			Usuario Usuario = UsuarioExistente.get();
			Usuario.setName(novoUsuario.getName());
			Usuario.setPassword(novoUsuario.getPassword());
			usuarioRepository.save(Usuario);
			return ResponseEntity.ok(Usuario);
		} else {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse ID não existe");
		}
	}
}
