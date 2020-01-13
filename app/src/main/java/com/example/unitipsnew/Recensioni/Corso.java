package com.example.unitipsnew.Recensioni;

public class Corso {
    String nomeCorso, nomeProfessore;
    int id, numeroRecensioni;

    public Corso() {
    }

    public Corso(String nomeCorso, String nomeProfessore, int numeroRecensioni, int id) {
        this.nomeCorso = nomeCorso;
        this.nomeProfessore = nomeProfessore;
        this.numeroRecensioni = numeroRecensioni;
        this.id = id;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public String getNomeProfessore() {
        return nomeProfessore;
    }

    public void setNomeProfessore(String nomeProfessore) {
        this.nomeProfessore = nomeProfessore;
    }

    public int getNumeroRecensioni() {
        return numeroRecensioni;
    }

    public void setNumeroRecensioni(int numeroRecensioni) {
        this.numeroRecensioni = numeroRecensioni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}