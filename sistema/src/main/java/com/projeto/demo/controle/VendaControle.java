package com.projeto.demo.controle;

import com.projeto.demo.modelos.Venda;
import com.projeto.demo.modelos.ItemVenda;
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
public class VendaControle {

    @Autowired private VendaRepositorio vendaRepositorio;
    @Autowired private ItemVendaRepositorio itemVendaRepositorio;
    @Autowired private ProdutoRepositorio produtoRepositorio;
    @Autowired private ClienteRepositorio clienteRepositorio;
    @Autowired private FuncionarioRepositorio funcionarioRepositorio;

    private List<ItemVenda> listaItemVenda = new ArrayList<>();

    @GetMapping("/cadastroVenda")
    public ModelAndView cadastrar(Venda venda, ItemVenda itemVenda) {
        ModelAndView mv = new ModelAndView("administrativo/Venda/cadastro");

        // BUG FIX: Limpa a lista se for uma venda nova
        if (venda.getId() == null) {
            this.listaItemVenda = new ArrayList<>();
        }

        mv.addObject("venda", venda);
        mv.addObject("itemVenda", itemVenda);
        mv.addObject("listaItemVendas", this.listaItemVenda);
        mv.addObject("listaClientes", clienteRepositorio.findAll());
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarVenda")
    public ModelAndView salvar(String acao, Venda venda, ItemVenda itemVenda) {
        if (acao.equals("itens")) {
            // CORRIGIDO: Usando itemVenda em vez de itemEntrada
            this.listaItemVenda.add(itemVenda);
            venda.setQuantidadeTotal(venda.getQuantidadeTotal() + itemVenda.getQuantidade());
            venda.setValorTotal(venda.getValorTotal() + (itemVenda.getQuantidade() * itemVenda.getValor()));

            return cadastrar(venda, new ItemVenda());

        } else if (acao.equals("salvar")) {
            vendaRepositorio.saveAndFlush(venda);

            for (ItemVenda it : listaItemVenda) {
                it.setVenda(venda);
                itemVendaRepositorio.saveAndFlush(it);

                // ATUALIZA ESTOQUE: Diminui a quantidade (SAÍDA)
                Optional<Produto> p = produtoRepositorio.findById(it.getProduto().getId());
                if (p.isPresent()) {
                    Produto produto = p.get();
                    produto.setEstoque(produto.getEstoque() - it.getQuantidade());
                    produtoRepositorio.saveAndFlush(produto);
                }
            }
            this.listaItemVenda = new ArrayList<>();
            return new ModelAndView("redirect:/listaVenda");
        }
        return cadastrar(venda, itemVenda);
    }

    @GetMapping("/listaVenda")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/Venda/lista");
        mv.addObject("listaVendas", vendaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarVenda/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Venda> venda = vendaRepositorio.findById(id);
        if (venda.isPresent()) {
            // Busca os itens salvos dessa venda no banco
            this.listaItemVenda = itemVendaRepositorio.findByVendaId(id);
            return cadastrar(venda.get(), new ItemVenda());
        }
        return new ModelAndView("redirect:/listaVenda");
    }

    @GetMapping("/removerVenda/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Venda> venda = vendaRepositorio.findById(id);
        if (venda.isPresent()) {
            // BUG FIX: Estorna o estoque antes de deletar a venda
            List<ItemVenda> itens = itemVendaRepositorio.findByVendaId(id);
            for (ItemVenda it : itens) {
                Produto p = it.getProduto();
                p.setEstoque(p.getEstoque() + it.getQuantidade()); // Devolve ao estoque
                produtoRepositorio.saveAndFlush(p);
                itemVendaRepositorio.delete(it);
            }
            vendaRepositorio.delete(venda.get());
        }
        return new ModelAndView("redirect:/listaVenda");
    }
}