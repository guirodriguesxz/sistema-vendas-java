package com.projeto.demo.controle;

import com.projeto.demo.modelos.Fornecedor;
import com.projeto.demo.repositorios.CidadeRepositorio;
import com.projeto.demo.repositorios.FornecedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class FornecedorControle {

    @Autowired
    private FornecedorRepositorio fornecedorRepositorio; // Ajustado para letra minúscula

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    @GetMapping("/cadastroFornecedor")
    public ModelAndView cadastrar(Fornecedor fornecedor) {
        // Caminho seguindo o padrão das outras pastas (F maiúsculo, sem 's')
        ModelAndView mv = new ModelAndView("administrativo/Fornecedor/cadastro");
        mv.addObject("fornecedor", fornecedor);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @GetMapping("/listaFornecedor")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/Fornecedor/lista");
        mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarFornecedor")
    public ModelAndView salvar(Fornecedor fornecedor, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(fornecedor);
        }
        fornecedorRepositorio.saveAndFlush(fornecedor);
        return listar();
    }

    @GetMapping("/editarFornecedor/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepositorio.findById(id);
        if (fornecedor.isPresent()) {
            return cadastrar(fornecedor.get());
        }
        return listar();
    }

    @GetMapping("/removerFornecedor/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        fornecedorRepositorio.deleteById(id);
        return listar();
    }
}