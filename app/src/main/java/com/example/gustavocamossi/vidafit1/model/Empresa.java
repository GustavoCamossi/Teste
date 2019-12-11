package com.example.gustavocamossi.vidafit1.model;

import com.example.gustavocamossi.vidafit1.firebase.AutenticadorFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

/**
 * Created by gustavocamossi on 23/10/19.
 */

public class Empresa implements Serializable {

    private String idUsuario;
    private String urlImagem;
    private String nome;
    private String tempo;
    private String categoria;
    private Double precoEntrega;

    public Empresa() {
    }

    public void salvar() {

        DatabaseReference firebaseRef = AutenticadorFirebase.getFirebase();
        DatabaseReference empresaRef = firebaseRef.child("empresas")
                .child(getIdUsuario());
        empresaRef.setValue(this);

    }
        public String getTempo () {
            return tempo;
        }

        public void setTempo (String tempo){
            this.tempo = tempo;
        }

        public String getCategoria () {
            return categoria;
        }

        public void setCategoria (String categoria){
            this.categoria = categoria;
        }

        public Double getPrecoEntrega () {
            return precoEntrega;
        }

        public void setPrecoEntrega (Double precoEntrega){
            this.precoEntrega = precoEntrega;
        }

        public String getIdUsuario () {
            return idUsuario;
        }

        public void setIdUsuario (String idUsuario){
            this.idUsuario = idUsuario;
        }

        public String getUrlImagem () {
            return urlImagem;
        }

        public void setUrlImagem (String urlImagem){
            this.urlImagem = urlImagem;
        }

        public String getNome () {
            return nome;
        }

        public void setNome (String nome){
            this.nome = nome;
        }
    }
