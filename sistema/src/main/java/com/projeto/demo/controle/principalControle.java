package com.projeto.demo.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class principalControle {
    // Comentei a linha abaixo para não dar conflito com o EstadoControle
    // @GetMapping("/administrativo")
    public String acessarPrincipal(){
        return "administrativo/home";
    }
}