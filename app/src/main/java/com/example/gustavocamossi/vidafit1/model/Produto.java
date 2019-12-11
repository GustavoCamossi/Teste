package com.example.gustavocamossi.vidafit1.model;

import com.example.gustavocamossi.vidafit1.firebase.AutenticadorFirebase;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by gustavocamossi on 23/10/19.
 */

public class Produto {

    private String idUsuario;
    private String idProduto;
    private String nome;
    private String descricao;
    private Double preco;
    private String urlImagem;

    public Produto() {
        DatabaseReference firebaseRef = AutenticadorFirebase.getFirebase();
        DatabaseReference produtoRef = firebaseRef.child("produtos");
        setIdProduto(produtoRef.push().getKey());
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public void salvar() {

        DatabaseReference firebaseRef = AutenticadorFirebase.getFirebase();
        DatabaseReference produtoRef = firebaseRef.child("produtos")
                .child(getIdUsuario())
                .child(getIdProduto());
        produtoRef.setValue(this);
    }

    public void remover() {
        DatabaseReference firebaseRef = AutenticadorFirebase.getFirebase();
        DatabaseReference produtosRef = firebaseRef
                .child("produtos")
                .child(getIdUsuario())
                .child(getIdProduto());
        produtosRef.removeValue();
    }


    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getIdUsuario() {
        return idUsuario;

    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
