package com.example.unitipsnew.Recensioni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

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
                openFilterCourses();
            }
        });
        return rootView;
    }

    private void openFilterCourses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.filter_corsi_choose, null);

        final TextView corso = (TextView) layout.findViewById(R.id.corso_choose);
        final TextView prof = (TextView) layout.findViewById(R.id.professore_choose);
        final TextView recens = (TextView) layout.findViewById(R.id.recensioni_choose);

        builder.setView(layout);

        final AlertDialog alertDialog = builder.create();



        alertDialog.show();
    }
}
