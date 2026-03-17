package com.projeto.demo.repositorios;

import com.projeto.demo.modelos.ItemEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemEntradaRepositorio extends JpaRepository<ItemEntrada, Long> {
    // Esse método vai buscar todos os itens de uma nota específica
    List<ItemEntrada> findByEntradaId(Long id);
}