package com.projeto.demo.repositorios;

import com.projeto.demo.modelos.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long> {

    // Essencial para o método editarVenda e removerVenda do seu controle
    List<ItemVenda> findByVendaId(Long id);
}