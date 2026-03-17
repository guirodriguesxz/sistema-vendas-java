package com.projeto.demo.controle;

import com.projeto.demo.modelos.Estado;
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
public class EstadoControle {

    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @GetMapping("/cadastroEstado")
    public ModelAndView cadastrar(Estado estado) {
        // AJUSTE: Caminho para a subpasta Estado
        ModelAndView mv = new ModelAndView("administrativo/Estado/cadastro");
        mv.addObject("estado", estado);
        return mv;
    }

    @GetMapping("/listaEstado")
    public ModelAndView listar() {
        // AJUSTE: Caminho para a subpasta Estado
        ModelAndView mv = new ModelAndView("administrativo/Estado/lista");
        mv.addObject("listaEstados", estadoRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarEstado")
    public ModelAndView salvar(Estado estado, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(estado);
        }
        estadoRepositorio.saveAndFlush(estado);
        return listar();
    }

    @GetMapping("/editarEstado/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Estado> estado = estadoRepositorio.findById(id);
        if (estado.isPresent()) {
            return cadastrar(estado.get());
        }
        return listar();
    }

    @GetMapping("/removerEstado/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        estadoRepositorio.deleteById(id);
        return listar();
    }
}