package com.example.spring.demo_spring_com_jpa_mysql.repository;

import com.example.spring.demo_spring_com_jpa_mysql.dao.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>, JpaSpecificationExecutor<Endereco> {
}
