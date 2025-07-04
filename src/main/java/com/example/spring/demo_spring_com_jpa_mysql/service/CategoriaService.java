package com.example.spring.demo_spring_com_jpa_mysql.service;

import com.example.spring.demo_spring_com_jpa_mysql.entity.Categoria;
import com.example.spring.demo_spring_com_jpa_mysql.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Transactional
    public List<Categoria> save(List<Categoria> categorias){
        return  this.repository.saveAll(categorias);
    }

    @Transactional(readOnly = true)
    public Categoria findByTitulo(String titulo){
        return  this.repository.findByTitulo(titulo).orElseGet(Categoria::new);
    }

    @Transactional(readOnly = true)
    public List<Categoria> findByInicioTitulo(String titulo){
        return this.repository.findByTituloStartsWith(titulo);
    }

    @Transactional(readOnly = true)
    public List<Categoria> findByTitulos(List<String> titulos){
        return this.repository.findByTituloIn(titulos);
    }

    @Transactional(readOnly = true)
    public List<Categoria> findAllOrderByTituloAsc(){
        return this.repository.findByOrderByTituloAsc();
    }
}
