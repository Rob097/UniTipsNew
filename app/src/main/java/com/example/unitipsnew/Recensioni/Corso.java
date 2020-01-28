package com.example.unitipsnew.Recensioni;

import java.util.ArrayList;

public class Corso {
    String nomeCorso, nomeProfessore;
    long id, numeroRecensioni;

    public Corso() {
    }

    public Corso(String nomeCorso, String nomeProfessore, long numeroRecensioni, long id) {
        this.nomeCorso = nomeCorso;
        this.nomeProfessore = nomeProfessore;
        this.numeroRecensioni = numeroRecensioni;
        this.id = id;
    }

    public Corso(String nomeCorso, String nomeProfessore) {
        this.nomeCorso = nomeCorso;
        this.nomeProfessore = nomeProfessore;
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

    public long getNumeroRecensioni() {
        return numeroRecensioni;
    }

    public void setNumeroRecensioni(long numeroRecensioni) {
        this.numeroRecensioni = numeroRecensioni;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
