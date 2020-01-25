package com.example.unitipsnew.Tips;

import java.util.ArrayList;

public class Tip {
    int id, like, dislike;
    long matricola;
    ArrayList<Commento> commenti;
    String titolo, testo, data;

    public Tip() {
    }

    public Tip(int id, long matricola, String titolo, String testo, int like, int dislike, ArrayList<Commento> commenti, String data) {
        this.id = id;
        this.matricola = matricola;
        this.like = like;
        this.dislike = dislike;
        this.titolo = titolo;
        this.testo = testo;
        this.commenti = commenti;
        this.data = data;
    }

    public Tip(long matricola, String titolo, String testo) {
        this.matricola = matricola;
        this.titolo = titolo;
        this.testo = testo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMatricola() {
        return matricola;
    }

    public void setMatricola(long matricola) {
        this.matricola = matricola;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public ArrayList<Commento> getCommenti() {
        return commenti;
    }

    public void setCommenti(ArrayList<Commento> commenti) {
        this.commenti = commenti;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
