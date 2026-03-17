package com.projeto.demo.controle;

import com.projeto.demo.modelos.Cliente;
import com.projeto.demo.repositorios.CidadeRepositorio;
import com.projeto.demo.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    @GetMapping("/cadastroCliente")
    public ModelAndView cadastrar(Cliente cliente) {
        // CORRIGIDO: Adicionado o 's' para bater com a pasta 'Clientes' da sua foto
        ModelAndView mv = new ModelAndView("administrativo/Clientes/cadastro");
        mv.addObject("cliente", cliente);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @GetMapping("/listaCliente")
    public ModelAndView listar() {
        // CORRIGIDO: Adicionado o 's' para bater com a pasta 'Clientes' da sua foto
        ModelAndView mv = new ModelAndView("administrativo/Clientes/lista");
        mv.addObject("listaClientes", clienteRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarCliente")
    public ModelAndView salvar(Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(cliente);
        }
        clienteRepositorio.saveAndFlush(cliente);
        return listar();
    }

    @GetMapping("/editarCliente/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        if (cliente.isPresent()) {
            return cadastrar(cliente.get());
        }
        return listar();
    }

    @GetMapping("/removerCliente/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        clienteRepositorio.deleteById(id);
        return listar();
    }
}