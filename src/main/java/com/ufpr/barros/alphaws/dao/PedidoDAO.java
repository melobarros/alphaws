package com.ufpr.barros.alphaws.dao;

import com.ufpr.barros.alphaws.connection.ConnectionFactory;
import com.ufpr.barros.alphaws.entities.Cliente;
import com.ufpr.barros.alphaws.entities.ItemDoPedido;
import com.ufpr.barros.alphaws.entities.Pedido;
import com.ufpr.barros.alphaws.entities.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private final String stmtInserir = "INSERT INTO pedido(data, id_cliente) VALUES(?,?)";
    private final String stmtConsultar = "SELECT * FROM pedido WHERE id_cliente = ?";
    private final String stmtListarItens = "SELECT produto.descricao, item_do_pedido.id_pedido, item_do_pedido.id_produto, item_do_pedido.quantidade from produto inner join item_do_pedido on produto.id = item_do_pedido.id_produto inner join pedido on item_do_pedido.id_pedido = pedido.id where pedido.id_cliente = ?";

    public int inserirPedido(Pedido pedido) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtInserir,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, pedido.getData());
            stmt.setInt(2, pedido.getCliente().getId());
            stmt.executeUpdate();
            
            return lerIdPedido(stmt);
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir um pedido no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    private int lerIdPedido(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }
    
    public Pedido consultaPedido(int id) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pedido pedidoLido = null;
        Cliente cliente;
        List<ItemDoPedido> ip = null;
        ClienteDAO cDAO = new ClienteDAO();
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtConsultar,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                cliente = cDAO.consultaClienteId(id);
                
                pedidoLido = new Pedido(rs.getString("data"), cliente, ip);
                pedidoLido.setId(rs.getInt("id"));
            }
            return pedidoLido;
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar pedido no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    
    
    public List<ItemDoPedido> listaItens(Pedido p) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ItemDoPedido> itensLidos = new ArrayList<>();
        ItemDoPedido ip = null;
        ProdutoDAO pDAO = new ProdutoDAO();
        Produto produto;
        
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtListarItens,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, p.getCliente().getId());
            rs = stmt.executeQuery();

            while(rs.next()){
                produto = pDAO.consultarProduto(Integer.parseInt(rs.getString("id_produto")));
                int quantidade = Integer.parseInt(rs.getString("quantidade"));
                
                ip = new ItemDoPedido(produto, p, quantidade);
                
                itensLidos.add(ip);
            }
            return itensLidos;
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar itens do pedido no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    
}
