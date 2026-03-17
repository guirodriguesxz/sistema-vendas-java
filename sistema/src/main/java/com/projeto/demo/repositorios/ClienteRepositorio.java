package com.projeto.demo.repositorios;
import com.projeto.demo.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {


}