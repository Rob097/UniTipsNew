package com.example.unitipsnew.Tips;

public class Commento {
    int id_commento, id_tip;
    long matricola;
    String testo, data;

    public Commento() {
    }

    public Commento(int id_commento, int id_tip, long matricola, String testo, String data) {
        this.id_commento = id_commento;
        this.id_tip = id_tip;
        this.matricola = matricola;
        this.testo = testo;
        this.data = data;
    }

    public int getId_commento() {
        return id_commento;
    }

    public void setId_commento(int id_commento) {
        this.id_commento = id_commento;
    }

    public int getId_tip() {
        return id_tip;
    }

    public void setId_tip(int id_tip) {
        this.id_tip = id_tip;
    }

    public long getMatricola() {
        return matricola;
    }

    public void setMatricola(long matricola) {
        this.matricola = matricola;
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
