package com.projeto.demo.modelos;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item_entrada") // CORRIGIDO: Mudei de "produto" para "item_entrada"
public class ItemEntrada implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // CORRIGIDO: Mudei de String para Long para o IDENTITY funcionar

    private double quantidade;
    private double valor;
    private double valorCusto; // DICA: É bom ter o custo unitário aqui também

    @ManyToOne(fetch = FetchType.EAGER)
    private Entrada entrada;

    @ManyToOne(fetch = FetchType.EAGER)
    private Produto produto;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(double valorCusto) {
        this.valorCusto = valorCusto;
    }
}