package com.example.unitipsnew.Recensioni;

import android.os.Parcelable;

import java.io.Serializable;

public class Recensione  {
    private String title;
    private String text;
    private String date;

    @Override
    public String toString() {
        return "Recensione{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public Recensione(){}

    public Recensione(String title) {
        this.title = title;
    }

    public Recensione(String _title, String _text, String _date){
        this.title=_title;
        this.text=_text;
        this.date=_date;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
