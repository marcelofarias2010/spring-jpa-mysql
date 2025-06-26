package com.example.spring.demo_spring_com_jpa_mysql.repository;

import com.example.spring.demo_spring_com_jpa_mysql.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByTitulo(String titulo);


    List<Categoria> findByTituloStartsWith(String titulo);

    List<Categoria> findByTituloIn(List<String> titulos);

    List<Categoria> findByOrderByTituloAsc();
}
