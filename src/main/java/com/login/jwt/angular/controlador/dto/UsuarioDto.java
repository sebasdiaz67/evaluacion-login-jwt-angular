package com.login.jwt.angular.controlador.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto implements Comparable<UsuarioDto> {

    private String id;
    @NonNull
	private String identificacion;
    @NonNull
	private String nombre;
    @NonNull
	private String nombreUsuario;
    @NonNull
	private String contrasenia;
	@NonNull
	private Integer edad;
	private String ciudad;
	@Builder.Default
    private List<RoleDto> roles = new ArrayList<>();

    @Override
    public int compareTo(UsuarioDto usuarioDto) {
        return this.nombreUsuario.compareTo(usuarioDto.nombreUsuario);
    }
}
