package com.projeto.demo.repositorios;
import com.projeto.demo.modelos.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {


}