package com.example.adegajg.modelo;

public class Produto {

    private int id;
    private String produto;
    private String preco;
    private String distribuidor;
    private String quantidade;

    public Produto(int id, String produto, String preco, String distribuidor, String quantidade) {
        this.id = id;
        this.produto = produto;
        this.preco = preco;
        this.distribuidor = distribuidor;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(String distribuidor) {
        this.distribuidor = distribuidor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}
