package com.example.spring.demo_spring_com_jpa_mysql.specification;

import com.example.spring.demo_spring_com_jpa_mysql.dao.Endereco;
import com.example.spring.demo_spring_com_jpa_mysql.entity.Autor;
import com.example.spring.demo_spring_com_jpa_mysql.entity.Post;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EnderecoSpecification {

    public static Specification<Endereco> likeUf(String uf) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("uf"), uf));
    }

    public static Specification<Endereco> likeCidade(String cidade) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("cidade"), cidade));
    }

    public static Specification<Endereco> likeLogradouro(String logradouro) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("logradouro"), "%" + logradouro + "%"));
    }

    public static Specification<Endereco> inCidades(List<String> cidades) {
        return ((root, query, criteriaBuilder)
                -> root.get("cidade").in(cidades));
    }

    public static Specification<Endereco> likeAutorNome(String nome) {
        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("autor").get("nome"), nome));
    }

    public static Specification<Endereco> likeAutorSobrenome(String sobrenome) {
        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("autor").get("sobrenome"), sobrenome));
    }

    public static Specification<Endereco> likeAutorNomeAndAutorSobrenome(String nome, String sobrenome) {
        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.and(
                criteriaBuilder.like(root.get("autor").get("nome"), nome),
                criteriaBuilder.like(root.get("autor").get("sobrenome"), sobrenome)
                )
        );
    }

    public static Specification<Endereco> byGreaterThanEqualToPosts(long total){
        return ((root, query, criteriaBuilder) -> {
            // Faz join entre Endereco e Autor
            Join<Endereco, Autor> autor = root.join("autor");
            // Faz join entre Autor e Post
            Join<Autor, Post> post = autor.join("posts");
            // Agrupa os posts por autor
            query.groupBy(post.get("autor"));
            //Conta o nº de posts por autor e aplica no having
            query.having(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(post.get("id")),total));

            return query.getRestriction();
        });
    }

}
