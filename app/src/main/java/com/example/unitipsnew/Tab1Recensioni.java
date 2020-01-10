package com.example.unitipsnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class Tab1Recensioni extends Fragment {

    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1recensioni, container, false);

        listview = (ListView) rootView.findViewById(R.id.listviewRecensioni);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Prima Recensione");
        arrayList.add("Seconda Recensione");
        arrayList.add("Terza Recensione");
        arrayList.add("Prima Recensione");
        arrayList.add("Seconda Recensione");
        arrayList.add("Terza Recensione");
        arrayList.add("Prima Recensione");
        arrayList.add("Seconda Recensione");
        arrayList.add("Terza Recensione");
        arrayList.add("Prima Recensione");
        arrayList.add("Seconda Recensione");
        arrayList.add("Terza Recensione");
        arrayList.add("Prima Recensione");
        arrayList.add("Seconda Recensione");
        arrayList.add("Terza Recensione");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listview.setAdapter(arrayAdapter);

        return rootView;
    }
}
