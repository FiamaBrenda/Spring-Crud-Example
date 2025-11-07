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

import com.login.exemplo.dto.UsuarioRequestDTO;
import com.login.exemplo.dto.UsuarioResponseDTO;
import com.login.exemplo.entity.Usuario;
import com.login.exemplo.repositories.UsuarioRepository;

import jakarta.validation.Valid;

//import dto.UsuarioResponseDTO;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@PostMapping(value = "/cadastro")
	public ResponseEntity<?> saveUser(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
		Usuario usuario = new Usuario(usuarioRequestDTO.getName(), usuarioRequestDTO.getEmail(),
				usuarioRequestDTO.getPassword());
		usuarioRepository.save(usuario);
		UsuarioResponseDTO user = new UsuarioResponseDTO(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody UsuarioRequestDTO user) {
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
	public List<UsuarioResponseDTO> listarUsuarios1() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioResponseDTO> listaDeUsuarios = usuarios.stream().map(UsuarioResponseDTO::new).toList();
//List<UsuarioResponseDTO> listaDeUsuarios = new ArrayList<>();
//		for (Usuario usuario : usuarios) {
//			listaDeUsuarios.add(new UsuarioResponseDTO(usuario));
//		}

		return listaDeUsuarios;
	}

//	@GetMapping(value = "listar/Vitinho")
//	public List<Usuario> listarUsuarios() {
//		return usuarioRepository.findAll();
//	}

	// return ResponseEntity.status(HttpStatus.CREATED).body(dto);

	@GetMapping(value = "{id}")
	public ResponseEntity<?> usuarioPorId(@PathVariable int id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
		}
		return ResponseEntity.ok(new UsuarioResponseDTO(usuario.get()));
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
	public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody UsuarioRequestDTO novoUsuario) {
		Optional<Usuario> UsuarioExistente = usuarioRepository.findById(id);

		if (UsuarioExistente.isPresent()) {
			Usuario usuario = UsuarioExistente.get();
			usuario.setName(novoUsuario.getName());
			usuario.setPassword(novoUsuario.getPassword());
			usuarioRepository.save(usuario);
			return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
		} else {
			// return ResponseEntity.notFound().build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse ID não existe");
		}
	}
}
