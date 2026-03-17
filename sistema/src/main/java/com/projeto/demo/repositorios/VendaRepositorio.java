package com.projeto.demo.repositorios;

import com.projeto.demo.modelos.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepositorio extends JpaRepository<Venda, Long> {
    // Aqui o Spring Data JPA já cria automaticamente o save, findById, findAll, etc.
}