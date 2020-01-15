package com.ufpr.barros.alphaws.controller;

import com.ufpr.barros.alphaws.dao.ClienteDAO;
import com.ufpr.barros.alphaws.entities.Cliente;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerCliente {
    ClienteDAO cDAO;
    
    public ControllerCliente(){
        cDAO = new ClienteDAO();
    }
    
    @PostMapping("/clientes")
    public String insereCliente(@RequestBody Cliente c) throws ClassNotFoundException{
        cDAO.inserirCliente(c);
        return "OK";
    }
    
    @PutMapping("/clientes")
    public String atualizaCliente(@RequestBody Cliente c) throws ClassNotFoundException{
        cDAO.atualizarCliente(c);
        return "OK";
    }
    
    /*
     Pedido pedidoLido = null;
        pedidoLido = pDAO.consultaPedido(c_.getId());

        if (pedidoLido != null) {
            addMsg("Excluir", "Cliente nao pode ser excluido!", FacesMessage.SEVERITY_INFO);
        } else {
     */
    
    @DeleteMapping("/clientes/{id}")
    public String excluiCliente(@PathVariable int id) throws ClassNotFoundException{
        cDAO.excluirCliente(id);
        return "OK";
    }
    
    @GetMapping("/clientes")
    public List<Cliente> listaClientes() throws ClassNotFoundException{
        return cDAO.listarClientes();
    }
    
    @GetMapping("/clientes/{id}")
    public Cliente consultaCliente(@PathVariable int id) throws ClassNotFoundException{
        return cDAO.consultaClienteId(id);
    }
}
