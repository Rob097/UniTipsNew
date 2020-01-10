package com.example.unitipsnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class Tab1Recensioni extends Fragment {

    List<Course> courses;
    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1recensioni, container, false);

        courses = new ArrayList<>();

        courses.add(new Course("Analisi Matematica 1", "Annalise Defranceschi", 50));
        courses.add(new Course("Analisi Matematica 2", "Annalise Defranceschi", 20));
        courses.add(new Course("Fisica", "Roberto Battiston", 15));
        courses.add(new Course("Programmazione Android", "Giuseppe Riccardi", 2));
        courses.add(new Course("Innovazione d'Impresa", "Luca Mezzetti", 6));

        listview = (ListView) rootView.findViewById(R.id.listviewRecensioni);
        CustomListAdapter adapter = new CustomListAdapter(this.getContext(), R.layout.list_item, courses);
        listview.setAdapter(adapter);

        return rootView;
    }
}
