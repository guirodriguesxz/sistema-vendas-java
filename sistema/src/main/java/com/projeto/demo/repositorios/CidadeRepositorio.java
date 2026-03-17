package com.projeto.demo.repositorios;
import com.projeto.demo.modelos.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {

}