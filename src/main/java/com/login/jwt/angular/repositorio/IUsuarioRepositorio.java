package com.login.jwt.angular.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.login.jwt.angular.entidades.Usuario;

@Repository
public interface IUsuarioRepositorio extends PagingAndSortingRepository<Usuario, String> {

	@Query("select u from Usuario u left join fetch u.roles r where u.nombreUsuario = ?1")
	Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
