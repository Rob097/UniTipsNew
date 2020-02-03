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

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Tab1Recensioni extends Fragment {

    List<Corso> courses;
    ListView listview;
    SharedPreferences sp;
    DatabaseHelper db;

    public Tab1Recensioni() {
    }

    public Tab1Recensioni(List<Corso> courses) {
        this.courses = courses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1recensioni, container, false);

        db = new DatabaseHelper(getActivity());
        courses = db.getAllCorsi();

        listview = (ListView) rootView.findViewById(R.id.listviewRecensioni);
        CustomListAdapterRecensioni adapter = new CustomListAdapterRecensioni(rootView.getContext(), R.layout.list_item_recensioni, courses);
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
        LayoutInflater inflater1 = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.filter_corsi_choose, null);

        final TextView corso = layout.findViewById(R.id.corso_choose);
        final TextView prof = layout.findViewById(R.id.professore_choose);
        final Button annulla = layout.findViewById(R.id.annulla_filter_corsi);

        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        corso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
                openFilterNameCorso();
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
                openFilterNameProf();
            }
        });


        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
    }

    private void openFilterNameCorso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        final LayoutInflater inflater1 = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.filter_corso, null);

        final EditText corso = layout.findViewById(R.id.nome_del_corso);
        final Button aggiorna = layout.findViewById(R.id.aggiorna_filter_corso);
        final Button annulla = layout.findViewById(R.id.annulla_filter_corso);

        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        aggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!corso.getText().toString().equals("")) {
                    List<Corso> corsi = db.getCorsiNome(corso.getText().toString());
                    openResults(corsi);
                    alertDialog.hide();
                }
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
    }

    private void openFilterNameProf() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        final LayoutInflater inflater1 = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.filter_corso, null);

        final EditText prof = layout.findViewById(R.id.nome_del_corso);
        final Button aggiorna = layout.findViewById(R.id.aggiorna_filter_corso);
        final Button annulla = layout.findViewById(R.id.annulla_filter_corso);
        final TextView titolo = layout.findViewById(R.id.tiolo_filter_corso);
        titolo.setText("Nome del Professore");
        prof.setHint("Nome del Professore");

        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        aggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!prof.getText().toString().equals("")) {
                    List<Corso> corsi = db.getCorsiProf(prof.getText().toString());
                    openResults(corsi);
                    alertDialog.hide();
                }
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
    }


    private void openResults(List<Corso> corsi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        final LayoutInflater inflater1 = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.tab1recensioni, null);

        ListView listview1 = layout.findViewById(R.id.listviewRecensioni);
        CustomListAdapterRecensioni adapter = new CustomListAdapterRecensioni(layout.getContext(), R.layout.list_item_recensioni, corsi);
        listview1.setAdapter(adapter);
        FloatingActionButton fab = layout.findViewById(R.id.fab_filter_recensioni);
        fab.hide();

        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
