package com.projeto.demo.modelos;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "entrada") // CORRIGIDO: Mudei de "produto" para "entrada"
public class Entrada implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // CORRIGIDO: Mudei de String para Long para o IDENTITY funcionar

    private String obs;
    private double valorTotal = 0.00;
    private double quantidadeTotal = 0.00;

    @Temporal(TemporalType.TIMESTAMP) // Boa prática para datas no JPA
    private Date dataEntrada = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.EAGER)
    private Funcionario funcionario;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(double quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}