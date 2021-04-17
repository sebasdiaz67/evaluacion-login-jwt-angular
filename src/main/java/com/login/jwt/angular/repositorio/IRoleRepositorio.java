package com.login.jwt.angular.repositorio;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.login.jwt.angular.entidades.Role;

@Repository
public interface IRoleRepositorio extends PagingAndSortingRepository<Role, String> {

    @Query("select r from Role r where r.nombre=:nombre")
    Optional<Role> findByNombre(@Param("nombre") String nombre);

}
