package com.projeto.demo.controle;

import com.projeto.demo.modelos.Produto;
import com.projeto.demo.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProdutoControle {

    @Autowired
    private ProdutoRepositorio produtoRepositorio; // Ajustado para minúsculo

    @GetMapping("/cadastroProduto")
    public ModelAndView cadastrar(Produto produto) {
        // Seguindo seu padrão: Pasta Produto (maiúsculo) e arquivo cadastro
        ModelAndView mv = new ModelAndView("administrativo/Produto/cadastro");
        mv.addObject("produto", produto); // Ajustado para minúsculo
        return mv;
    }

    @GetMapping("/listaProduto")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/Produto/lista");
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarProduto")
    public ModelAndView salvar(Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(produto);
        }

        // DICA: Se quiser calcular o lucro automático antes de salvar:
        // produto.setLucro(produto.getPrecoVenda() - produto.getPrecoCusto());

        produtoRepositorio.saveAndFlush(produto);
        return listar();
    }

    @GetMapping("/editarProduto/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        if (produto.isPresent()) {
            return cadastrar(produto.get());
        }
        return listar();
    }

    @GetMapping("/removerProduto/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        produtoRepositorio.deleteById(id);
        return listar();
    }
}