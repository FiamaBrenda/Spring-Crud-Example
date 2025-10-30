package dto;

import com.login.exemplo.entity.Usuario;

public class UsuarioResponseDTO {

	private int id;
	private String name;
	private String email;

	public UsuarioResponseDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.name = usuario.getName();
		this.email = usuario.getEmail();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

}