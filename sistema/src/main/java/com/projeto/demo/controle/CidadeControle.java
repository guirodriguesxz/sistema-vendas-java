package com.projeto.demo.controle;

import com.projeto.demo.modelos.Cidade;
import com.projeto.demo.repositorios.CidadeRepositorio;
import com.projeto.demo.repositorios.EstadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CidadeControle {

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @GetMapping("/cadastroCidade")
    public ModelAndView cadastrar(Cidade cidade) {
        // AJUSTE: Garante que a pasta no IntelliJ seja 'Cidade' com C maiúsculo
        ModelAndView mv = new ModelAndView("administrativo/Cidade/cadastro");
        mv.addObject("cidade", cidade);
        mv.addObject("listaEstados", estadoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/listaCidade")
    public ModelAndView listar() {
        // AJUSTE: Garante que a pasta no IntelliJ seja 'Cidade' com C maiúsculo
        ModelAndView mv = new ModelAndView("administrativo/Cidade/lista");
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarCidade")
    public ModelAndView salvar(Cidade cidade, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(cidade);
        }
        cidadeRepositorio.saveAndFlush(cidade);
        return listar();
    }

    @GetMapping("/editarCidade/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cidade> cidade = cidadeRepositorio.findById(id);
        if (cidade.isPresent()) {
            return cadastrar(cidade.get());
        }
        return listar();
    }

    @GetMapping("/removerCidade/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        cidadeRepositorio.deleteById(id);
        return listar();
    }
}