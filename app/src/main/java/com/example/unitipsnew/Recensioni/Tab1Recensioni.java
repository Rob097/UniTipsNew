package com.example.unitipsnew.Recensioni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import androidx.fragment.app.Fragment;

public class Tab1Recensioni extends Fragment{

    List<Corso> courses;
    ListView listview;
    SharedPreferences sp;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1recensioni, container, false);

        db = new DatabaseHelper(getActivity());
        courses = db.getAllCorsi();

        listview = (ListView) rootView.findViewById(R.id.listviewRecensioni);
        CustomListAdapterRecensioni adapter = new CustomListAdapterRecensioni(this.getContext(), R.layout.list_item_recensioni, courses);
        listview.setAdapter(adapter);

        //Funzione Float
        FloatingActionButton fab = rootView.findViewById(R.id.fab_filter_recensioni);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action of Recensioni", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        return rootView;
    }
}
