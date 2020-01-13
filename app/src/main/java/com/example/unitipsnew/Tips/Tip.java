package com.example.unitipsnew.Tips;

public class Tip {
    int id, like, dislike, commenti;
    String titolo, testo;

    public Tip() {
    }

    public Tip(int id, String titolo, String testo, int like, int dislike, int commenti) {
        this.id = id;
        this.like = like;
        this.dislike = dislike;
        this.titolo = titolo;
        this.testo = testo;
        this.commenti = commenti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCommenti() {
        return commenti;
    }

    public void setCommenti(int commenti) {
        this.commenti = commenti;
    }
}
