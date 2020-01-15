package com.ufpr.barros.alphaws.controller;

import com.ufpr.barros.alphaws.dao.ClienteDAO;
import com.ufpr.barros.alphaws.dao.PedidoDAO;
import com.ufpr.barros.alphaws.entities.Cliente;
import com.ufpr.barros.alphaws.entities.ItemDoPedido;
import com.ufpr.barros.alphaws.entities.Pedido;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerPedido {
    PedidoDAO pDAO;
    ClienteDAO cDAO;
    
    public ControllerPedido(){
        pDAO = new PedidoDAO();
        cDAO = new ClienteDAO();
    }
    
    @GetMapping("/pedidos")
    public List<Pedido> listaPedidos() throws ClassNotFoundException{
    	List<Pedido> pedidos = new ArrayList<>();
    	Pedido p;
    	
    	List<Cliente> clientes = cDAO.listarClientes();
    	for(Cliente c : clientes) {
        	p = pDAO.consultaPedido(c.getId());
    		if(p != null) {
    			pedidos.add(p);
    		}
    	}
    	
    	return pedidos;
    }
    
    @GetMapping("/pedidos/{id}")
    public Pedido consultaPedido(@PathVariable int id) throws ClassNotFoundException{
    	List<Cliente> clientes = cDAO.listarClientes();
    	Pedido p = null;
    	
    	for(Cliente c : clientes) {
        	p = pDAO.consultaPedido(c.getId());
    		if(p != null) {
    			if(p.getId() == id) {
    				return p;
    			}
    		}
    	}
    	return null;
    }
    
    @GetMapping("/pedidos/cliente/{cpf}")
    public Pedido consultaPedidoCpf(@PathVariable String cpf) throws ClassNotFoundException{
    	Cliente c = null;
    	Pedido p = null;
    	ClienteDAO cDAO = new ClienteDAO();
    	PedidoDAO pDAO = new PedidoDAO();
    	
    	c = cDAO.consultaCliente(cpf);
    	if(c != null) {
    		p = pDAO.consultaPedido(c.getId());
    	}
    	return p;
    }
    
    @PostMapping("/pedidos")
    public int inserirPedido(@RequestBody Pedido p) throws ClassNotFoundException{
        return pDAO.inserirPedido(p);
    }
    
    @GetMapping("/pedidos/cpf/{cpf}")
    public List<ItemDoPedido> listaItens(@PathVariable String cpf) throws ClassNotFoundException{
    	Cliente c = null;
    	Pedido p = null;
    	ClienteDAO cDAO = new ClienteDAO();
    	PedidoDAO pDAO = new PedidoDAO();
    	
    	c = cDAO.consultaCliente(cpf);
    	if(c != null) {
    		p = pDAO.consultaPedido(c.getId());
    		
    		if(p != null) {
    			return pDAO.listaItens(p);
    		}
    	}
    	
        return null;
    }
}
