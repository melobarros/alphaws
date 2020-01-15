package com.ufpr.barros.alphaws.entities;


public class Produto {
    int id = -1;
    String descricao;
    

     public Produto() {
    	super();
    }

    
    public Produto(String descricao){
        this.descricao = descricao;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getDescricao(){
        return descricao;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
}
