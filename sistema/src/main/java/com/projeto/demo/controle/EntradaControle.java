package com.projeto.demo.controle;

import com.projeto.demo.modelos.Entrada;
import com.projeto.demo.modelos.ItemEntrada;
import com.projeto.demo.modelos.Produto;
import com.projeto.demo.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaControle {

    @Autowired
    private EntradaRepositorio entradaRepositorio;
    @Autowired
    private ItemEntradaRepositorio itemEntradaRepositorio;
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;
    @Autowired
    private FornecedorRepositorio fornecedorRepositorio;

    private List<ItemEntrada> listaItemEntrada = new ArrayList<>();

    @GetMapping("/cadastroEntrada")
    public ModelAndView cadastrar(Entrada entrada, ItemEntrada itemEntrada) {
        ModelAndView mv = new ModelAndView("administrativo/Entrada/cadastro");

        // BUG FIX: Se for uma entrada nova (id nulo), limpamos a lista da memória
        if (entrada.getId() == null) {
            this.listaItemEntrada = new ArrayList<>();
        }

        mv.addObject("entrada", entrada);
        mv.addObject("itemEntrada", itemEntrada);
        mv.addObject("listaItemEntradas", this.listaItemEntrada);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarEntrada")
    public ModelAndView salvar(String acao, Entrada entrada, ItemEntrada itemEntrada) {

        if (acao.equals("itens")) {
            // Adiciona o item na lista temporária
            this.listaItemEntrada.add(itemEntrada);

            // Atualiza os totais da "capa" da entrada
            entrada.setQuantidadeTotal(entrada.getQuantidadeTotal() + itemEntrada.getQuantidade());
            entrada.setValorTotal(entrada.getValorTotal() + (itemEntrada.getQuantidade() * itemEntrada.getValor()));

            return cadastrar(entrada, new ItemEntrada());

        } else if (acao.equals("salvar")) {
            // Salva a entrada principal
            entradaRepositorio.saveAndFlush(entrada);

            for (ItemEntrada it : listaItemEntrada) {
                it.setEntrada(entrada);
                itemEntradaRepositorio.saveAndFlush(it);

                // ATUALIZA ESTOQUE: Soma o que entrou no produto
                Optional<Produto> p = produtoRepositorio.findById(it.getProduto().getId());
                if (p.isPresent()) {
                    Produto produto = p.get();
                    produto.setEstoque(produto.getEstoque() + it.getQuantidade());
                    produtoRepositorio.saveAndFlush(produto);
                }
            }

            this.listaItemEntrada = new ArrayList<>();
            return listar();
        }

        return new ModelAndView("redirect:/cadastroEntrada");
    }

    @GetMapping("/listaEntrada")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/Entrada/lista");
        mv.addObject("listaEntradas", entradaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarEntrada/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Entrada> entrada = entradaRepositorio.findById(id);
        if (entrada.isPresent()) {
            // Busca os itens que já pertencem a essa entrada para mostrar na tabela
            this.listaItemEntrada = itemEntradaRepositorio.findByEntradaId(id);
            return cadastrar(entrada.get(), new ItemEntrada());
        }
        return listar();
    }

    @GetMapping("/removerEntrada/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Entrada> entrada = entradaRepositorio.findById(id);
        if (entrada.isPresent()) {
            // BUG FIX: Antes de remover a entrada, precisamos estornar o estoque!
            List<ItemEntrada> itens = itemEntradaRepositorio.findByEntradaId(id);
            for (ItemEntrada it : itens) {
                Produto p = it.getProduto();
                p.setEstoque(p.getEstoque() - it.getQuantidade());
                produtoRepositorio.saveAndFlush(p);
                itemEntradaRepositorio.delete(it);
            }
            entradaRepositorio.delete(entrada.get());
        }
        return listar();
    }
}