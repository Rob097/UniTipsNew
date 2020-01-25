package com.example.unitipsnew.Tips;

import java.util.ArrayList;

public class Tip {
    int id;
    ArrayList<Long> like, dislike;
    ArrayList<Commento> commenti;
    String titolo, testo, data;

    public Tip() {
    }

    public Tip(int id, ArrayList<Long> like, ArrayList<Long> dislike, ArrayList<Commento> commenti, String titolo, String testo, String data) {
        this.id = id;
        this.like = like;
        this.dislike = dislike;
        this.commenti = commenti;
        this.titolo = titolo;
        this.testo = testo;
        this.data = data;
    }

    public Tip(String titolo, String testo) {
        this.titolo = titolo;
        this.testo = testo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Long> getLike() {
        return like;
    }

    public void setLike(ArrayList<Long> like) {
        this.like = like;
    }

    public ArrayList<Long> getDislike() {
        return dislike;
    }

    public void setDislike(ArrayList<Long> dislike) {
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
