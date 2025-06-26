package com.example.spring.demo_spring_com_jpa_mysql.service;

import com.example.spring.demo_spring_com_jpa_mysql.dao.Endereco;
import com.example.spring.demo_spring_com_jpa_mysql.repository.EnderecoRepository;
import com.example.spring.demo_spring_com_jpa_mysql.specification.EnderecoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository repository;

    @Transactional
    public Endereco save(Endereco endereco){
        return this.repository.save(endereco);
    }

    @Transactional
    public List<Endereco> findByUfAndCidade(String uf, String cidade){
        Specification<Endereco> spec = Specification.where(
                EnderecoSpecification.likeUf(uf).and(EnderecoSpecification.likeCidade(cidade))
        );
        return this.repository.findAll(spec);
    }

    @Transactional
    public List<Endereco> findByUfAndLogradouro(String uf, String logradouro){
        Specification<Endereco> spec = Specification.where(
                EnderecoSpecification.likeUf(uf).and(EnderecoSpecification.likeLogradouro(logradouro))
        );
        return this.repository.findAll(spec);
    }

    @Transactional
    public List<Endereco> findByUfAndCidades(String uf, List<String> cidades){
        Specification<Endereco> spec = Specification.where(
                EnderecoSpecification.likeUf(uf).and(EnderecoSpecification.inCidades(cidades))
        );
        return this.repository.findAll(spec);
    }

    @Transactional
    public List<Endereco> findByAutorNomeOrSobrenome(String nome, String sobrenome){
        Specification<Endereco> spec = Specification.where(
                EnderecoSpecification.likeAutorNome(nome).or(EnderecoSpecification.likeAutorSobrenome(sobrenome))
        );
        return this.repository.findAll(spec);
    }

    @Transactional
    public Endereco findByAutorNomeAndSobrenome(String nome, String sobrenome){

        return this.repository.findOne(EnderecoSpecification.likeAutorNomeAndAutorSobrenome(nome, sobrenome))
                .orElseGet(Endereco::new);
    }

    @Transactional(readOnly = true)
    public List<Endereco> findByAutorTotalDePostsAndCidades(long total, List<String> cidades){
        Specification<Endereco> spec = Specification.where(
                EnderecoSpecification.inCidades(cidades).and(EnderecoSpecification.byGreaterThanEqualToPosts(total))
        );
        return this.repository.findAll(spec);
    }
}
