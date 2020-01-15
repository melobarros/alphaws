package com.ufpr.barros.alphaws.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufpr.barros.alphaws.dao.ClienteDAO;
import com.ufpr.barros.alphaws.dao.ItemDoPedidoDAO;
import com.ufpr.barros.alphaws.dao.PedidoDAO;
import com.ufpr.barros.alphaws.dao.ProdutoDAO;
import com.ufpr.barros.alphaws.entities.Cliente;
import com.ufpr.barros.alphaws.entities.ItemDoPedido;
import com.ufpr.barros.alphaws.entities.Pedido;
import com.ufpr.barros.alphaws.entities.Produto;

@RestController
public class ControllerItem {
	ItemDoPedidoDAO ipDAO = new ItemDoPedidoDAO();
    ClienteDAO cDAO = new ClienteDAO();
    ProdutoDAO pDAO = new ProdutoDAO();
    PedidoDAO peDAO = new PedidoDAO();

    Cliente c = new Cliente("", "", "");
    Pedido p;
    Produto prod = null;
    int id;
    int qtd = 1;
    
    public ControllerItem(){
    	ipDAO = new ItemDoPedidoDAO();
    	cDAO = new ClienteDAO();
    	pDAO = new ProdutoDAO();
    	peDAO = new PedidoDAO();
    }
    
    @PostMapping("/item")
    public void inserirItem(ItemDoPedido ip) throws ClassNotFoundException{
    	
    	Cliente c = cDAO.consultaCliente(ip.getPedido().getCliente().getCpf());
    	if(c != null){
            p = peDAO.consultaPedido(c.getId());
            
            if(prod != null){
                if(p != null){
                    ip = new ItemDoPedido(prod, p, qtd);
                    ipDAO.inserirItemDoPedido(ip);
                    //addMsg("Sucesso", "Item inserido com sucesso!", FacesMessage.SEVERITY_INFO);
                }else{
                    p = new Pedido("Data", c, null);
                    id = peDAO.inserirPedido(p);
                    p.setId(id);

                    ip = new ItemDoPedido(prod, p, qtd);
                    ipDAO.inserirItemDoPedido(ip);
                    //addMsg("Sucesso", "Pedido criado e Item inserido com sucesso!", FacesMessage.SEVERITY_INFO);
                }
            }else{
                //addMsg("Selecione", "Selecione um produto!", FacesMessage.SEVERITY_INFO);
            }
            
        }else{
            //addMsg("Consulta", "Cliente nao nao existe!", FacesMessage.SEVERITY_INFO);
        }
    }
}
