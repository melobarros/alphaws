package com.ufpr.barros.alphaws.dao;

import com.ufpr.barros.alphaws.connection.ConnectionFactory;
import com.ufpr.barros.alphaws.entities.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final String stmtInserir = "INSERT INTO cliente(nome, sobrenome, cpf) VALUES(?,?,?)";
    private final String stmtConsultar = "SELECT * FROM cliente WHERE cpf = ?";
    private final String stmtConsultarId = "SELECT * FROM cliente WHERE id = ?";
    private final String stmtListar = "SELECT * FROM cliente";
    private final String stmtExcluir = "DELETE FROM cliente WHERE id = ?";
    private final String stmtAtualizar = "UPDATE cliente SET nome = ?, sobrenome = ?, cpf = ? WHERE id = ?";
    
    public void inserirCliente(Cliente cliente) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtInserir,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getCpf());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir um produto no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public Cliente consultaCliente(String cpf) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente clienteLido = null;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtConsultar,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            while(rs.next()){
                clienteLido = new Cliente(rs.getString("nome"), rs.getString("sobrenome"), rs.getString("cpf"));
                clienteLido.setId(rs.getInt("id"));
            }
            return clienteLido;
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar clientes no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public Cliente consultaClienteId(int id) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente clienteLido = null;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtConsultarId,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                clienteLido = new Cliente(rs.getString("nome"), rs.getString("sobrenome"), rs.getString("cpf"));
                clienteLido.setId(rs.getInt("id"));
            }
            return clienteLido;
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar cliente no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public List<Cliente> listarClientes() throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> clientesLidos = new ArrayList<>();
        Cliente c;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtListar,PreparedStatement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();

            while(rs.next()){
                c = new Cliente(rs.getString("nome"), rs.getString("sobrenome"), rs.getString("cpf"));
                c.setId(rs.getInt("id"));
                clientesLidos.add(c);
            }
            return clientesLidos;
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar clientes no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public void atualizarCliente(Cliente cliente) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtAtualizar,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getCpf());
            stmt.setInt(4, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar um cliente no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public void excluirCliente(int id) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtExcluir,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao excluir um cliente no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    
}
