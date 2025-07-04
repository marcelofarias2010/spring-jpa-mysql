package com.example.spring.demo_spring_com_jpa_mysql.service;

import com.example.spring.demo_spring_com_jpa_mysql.entity.Autor;
import com.example.spring.demo_spring_com_jpa_mysql.entity.Categoria;
import com.example.spring.demo_spring_com_jpa_mysql.entity.Post;
import com.example.spring.demo_spring_com_jpa_mysql.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AutorService autorService;
    @Autowired
    private CategoriaService categoriaService;

    @Transactional
    public Post save(Post post){
        // primeiro precisamos obter o id de autor
        Autor autor = this.autorService.findById(post.getAutor().getId());
        post.setAutor(autor);
        // separa cada objeto em json por titulo e transforma em lista de titulos
        List<String> titulos = post.getCategorias().stream().map(Categoria::getTitulo).toList();
        List<Categoria> categorias = this.categoriaService.findByTitulos(titulos);
        post.setCategorias(categorias);

        return this.postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllByCategoriaAndAutorId(String categoria, Long autorId){
        return this.postRepository.findByCategoriasTituloAndAutorId(categoria,autorId);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllByAutor(String autorNome, String autorSobrenome){
        return this.postRepository.findByAutorNomeOrAutorSobrenome(autorNome,autorSobrenome);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllByTitulo(String titulo){
        return this.postRepository.findByTituloContainsOrderByAutorNomeAsc(titulo);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllByDataPublicacaoMaiorOuIgual(LocalDate data){
        return this.postRepository.findByDataPublicacaoIsGreaterThanEqual(data);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllBySemDataPublicacao(){
        return this.postRepository.findByDataPublicacaoIsNull();
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPagination(Pageable pageable){
        return this.postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllByAno(int ano, int page, int size, String sort, String dir){
        Pageable pageable =  PageRequest.of(page,size, Sort.Direction.fromString(dir), sort);

        return this.postRepository.findByAno(ano,pageable);
    }
}
