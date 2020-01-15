package com.ufpr.barros.alphaws.dao;

import com.ufpr.barros.alphaws.connection.ConnectionFactory;
import com.ufpr.barros.alphaws.entities.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private final String stmtInserir = "INSERT INTO produto(descricao) VALUES(?)";
    private final String stmtConsultar = "SELECT * FROM produto WHERE id = ?";
    private final String stmtListar = "SELECT * FROM produto";
    private final String stmtExcluir = "DELETE FROM produto WHERE id = ?";
    private final String stmtAtualizar = "UPDATE produto SET descricao = ? WHERE id = ?";
    
    public void inserirProduto(Produto produto) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtInserir,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produto.getDescricao());
            stmt.executeUpdate();
            //produto.setId(lerIdProduto(stmt));
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir um produto no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    private int lerIdProduto(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }
    
    public Produto consultarProduto(int id) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto produtoLido;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtConsultar,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()){
                produtoLido = new Produto(rs.getString("descricao"));
                produtoLido.setId(rs.getInt("id"));
                return produtoLido;
            }else{
                throw new RuntimeException("Não existe produto com este id. Id="+id);
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar um produto no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public List<Produto> listarProdutos() throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtosLidos = new ArrayList<>();
        Produto p;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtListar,PreparedStatement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();

            while(rs.next()){
                p = new Produto(rs.getString("descricao"));
                p.setId(rs.getInt("id"));
                produtosLidos.add(p);
            }
            return produtosLidos;
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar produtos no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public void excluirProduto(int id) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtExcluir,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao excluir um produto no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
    
    public void atualizarProduto(Produto produto) throws ClassNotFoundException{
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtAtualizar,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produto.getDescricao());
            stmt.setInt(2, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar um produto no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
    }
}
