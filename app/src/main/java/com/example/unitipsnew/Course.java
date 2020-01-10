package com.example.unitipsnew;

public class Course {
    String nomeCorso, nomeProfessore;
    int numeroRecensioni;

    public Course(String nomeCorso, String nomeProfessore, int numeroRecensioni) {
        this.nomeCorso = nomeCorso;
        this.nomeProfessore = nomeProfessore;
        this.numeroRecensioni = numeroRecensioni;
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
}
