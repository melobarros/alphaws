package com.ufpr.barros.alphaws.controller;

import com.ufpr.barros.alphaws.dao.ProdutoDAO;
import com.ufpr.barros.alphaws.entities.Produto;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerProduto {
    ProdutoDAO pDAO;
    
    public ControllerProduto(){
        pDAO = new ProdutoDAO();
    }
    
    @GetMapping("/produtos/{id}")
    public Produto consultaProduto(@PathVariable int id) throws ClassNotFoundException{
        return pDAO.consultarProduto(id);
    }
    
    @GetMapping("/produtos")
    public List<Produto> listarProdutos() throws ClassNotFoundException{
        return pDAO.listarProdutos();
    }
    
    @PostMapping("/produtos")
    public String inserirProduto(@RequestBody Produto p) throws ClassNotFoundException{
        pDAO.inserirProduto(p);
        return "OK";
    }
    
    @PutMapping("/produtos")
    public String atualizarProduto(@RequestBody Produto p) throws ClassNotFoundException{
        pDAO.atualizarProduto(p);
        return "OK";
    }
    
    @DeleteMapping("/produtos/{id}")
    public String excluirProduto(@PathVariable int id) throws ClassNotFoundException{
        pDAO.excluirProduto(id);
        return "OK";
    }
}
