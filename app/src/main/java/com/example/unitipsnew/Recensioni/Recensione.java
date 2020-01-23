package com.example.unitipsnew.Recensioni;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Recensione {
    long id_recensione, id_corso, matricola;
    private String title;
    private String text;
    private String date;

    public Recensione() {
    }

    public Recensione(String title) {
        this.title = title;
    }

    public Recensione(long id_recensione, long id_corso, long matricola, String title, String text, String date) {
        this.id_recensione = id_recensione;
        this.id_corso = id_corso;
        this.matricola = matricola;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public Recensione(long id_recensione, long id_corso, long matricola, String title, String text) {
        this.id_recensione = id_recensione;
        this.id_corso = id_corso;
        this.matricola = matricola;
        this.title = title;
        this.text = text;
    }

    public long getId_recensione() {
        return id_recensione;
    }

    public void setId_recensione(long id_recensione) {
        this.id_recensione = id_recensione;
    }

    public long getId_corso() {
        return id_corso;
    }

    public void setId_corso(long id_corso) {
        this.id_corso = id_corso;
    }

    public long getMatricola() {
        return matricola;
    }

    public void setMatricola(long matricola) {
        this.matricola = matricola;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Recensione{" +
                "id_recensione=" + id_recensione +
                ", id_corso=" + id_corso +
                ", matricola=" + matricola +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
