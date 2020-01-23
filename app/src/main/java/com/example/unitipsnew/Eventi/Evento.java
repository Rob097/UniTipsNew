package com.example.unitipsnew.Eventi;

import java.sql.Timestamp;

public class Evento {
    int id, immagine, interessati;
    String titolo, descrizione, luogo, data;

    public Evento() {
    }

    public Evento(int id, int immagine, int interessati, String titolo, String descrizione, String luogo, String data) {
        this.id = id;
        this.immagine = immagine;
        this.interessati = interessati;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.luogo = luogo;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImmagine() {
        return immagine;
    }

    public void setImmagine(int immagine) {
        this.immagine = immagine;
    }

    public int getInteressati() {
        return interessati;
    }

    public void setInteressati(int interessati) {
        this.interessati = interessati;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
