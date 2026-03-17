package com.projeto.demo.controle;

import com.projeto.demo.modelos.Funcionario;
import com.projeto.demo.repositorios.FuncionarioRepositorio;
import com.projeto.demo.repositorios.CidadeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class FuncionarioControle {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    // RECOLOCADO: Esta é a rota que abre o menu principal do sistema
    @GetMapping("/administrativo")
    public ModelAndView administrativo() {
        return new ModelAndView("administrativo/home");
    }

    @GetMapping("/cadastroFuncionario")
    public ModelAndView cadastrar(Funcionario funcionario) {
        // Mantido o padrão com 'F' maiúsculo
        ModelAndView mv = new ModelAndView("administrativo/Funcionario/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @GetMapping("/listaFuncionario")
    public ModelAndView listar() {
        // Mantido o padrão com 'F' maiúsculo
        ModelAndView mv = new ModelAndView("administrativo/Funcionario/lista");
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarFuncionario")
    public ModelAndView salvar(Funcionario funcionario, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(funcionario);
        }
        funcionarioRepositorio.saveAndFlush(funcionario);
        return listar();
    }

    @GetMapping("/editarFuncionario/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        if (funcionario.isPresent()) {
            return cadastrar(funcionario.get());
        }
        return listar();
    }

    @GetMapping("/removerFuncionario/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        funcionarioRepositorio.deleteById(id);
        return listar();
    }
}