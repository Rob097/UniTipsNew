package com.example.unitipsnew.Eventi;

import java.sql.Timestamp;

public class Evento {
    String titolo, luogo;
    Timestamp data;
    int immagine;

    public Evento() {
    }

    public Evento(String titolo, String luogo, Timestamp data, int immagine) {
        this.titolo = titolo;
        this.luogo = luogo;
        this.data = data;
        this.immagine = immagine;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getImmagine() {
        return immagine;
    }

    public void setImmagine(int immagine) {
        this.immagine = immagine;
    }
}
