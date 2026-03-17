package com.projeto.demo.repositorios;
import com.projeto.demo.modelos.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

}