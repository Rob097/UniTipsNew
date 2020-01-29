package com.example.unitipsnew.Utente;

public class Utente {
    long matricola;
    String email, nome, cognome, password, immagine;

    public Utente() {
    }

    public Utente(long matricola, String email, String nome, String cognome, String password, String immagine) {
        this.matricola = matricola;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.immagine = immagine;
    }

    public long getMatricola() {
        return matricola;
    }

    public void setMatricola(long matricola) {
        this.matricola = matricola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "matricola=" + matricola +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", password='" + password + '\'' +
                ", immagine=" + immagine +
                '}';
    }
}
