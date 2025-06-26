package com.example.spring.demo_spring_com_jpa_mysql.controller;

import com.example.spring.demo_spring_com_jpa_mysql.entity.Categoria;
import com.example.spring.demo_spring_com_jpa_mysql.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService service;

    @PostMapping
    public List<Categoria> salvar(@RequestBody List<Categoria> categorias){
        return this.service.save(categorias);
    }

    @GetMapping("titulo/{titulo}")
    public Categoria getByTitulo(@PathVariable String titulo){
        return this.service.findByTitulo(titulo);
    }

    @GetMapping("titulo/inicio/{titulo}")
    public List<Categoria> getByInicioTitulo(@PathVariable String titulo){
        return this.service.findByInicioTitulo(titulo);
    }

    @GetMapping("titulos")
    public List<Categoria> getByTitulos(@RequestParam(name = "t") List<String> titulos){
        return this.service.findByTitulos(titulos);
    }

    @GetMapping()
    public List<Categoria> getCategoriasOrdenadasAsc(){
        return this.service.findAllOrderByTituloAsc();
    }
}
