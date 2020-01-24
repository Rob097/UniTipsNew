package com.example.unitipsnew.Eventi;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;
import com.example.unitipsnew.Recensioni.Recensione;
import com.example.unitipsnew.Utente.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Tab2Eventi extends Fragment {

    List<Evento> eventi;
    ListView listview;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2eventi, container, false);

        db = new DatabaseHelper(getActivity());
        eventi = db.getAllEventi();

        /*eventi.add(new Evento("Festa Universitaria Centrale", "Trento", new Timestamp(new Date().getTime()), R.drawable.fuc_unitn, 1));
        eventi.add(new Evento("Career Fair", "Trento", new Timestamp(new Date().getTime()), R.drawable.careerfair_unitn, 15));
        eventi.add(new Evento("Festa Universitaria Centrale", "Trento", new Timestamp(new Date().getTime()), R.drawable.fuc_unitn, 7));
        eventi.add(new Evento("Career Fair", "Trento", new Timestamp(new Date().getTime()), R.drawable.careerfair_unitn, 0));*/

        listview = (ListView) rootView.findViewById(R.id.listviewEventi);
        CustomListAdapterEventi adapterEventi = new CustomListAdapterEventi(this.getContext(), R.layout.list_item_eventi, eventi);
        listview.setAdapter(adapterEventi);

        FloatingActionButton fab = rootView.findViewById(R.id.fab_filter_eventi);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptionsMenu();
            }
        });

        return rootView;
    }

    private void openOptionsMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        builder.setTitle("OPZIONI");

        builder.setPositiveButton("Aggiungi nuovo Evento", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                openNewEvento();
            }
        });

        builder.setNegativeButton("Filtra Eventi", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openNewEvento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.new_evento_form, null);

        Button cancel = (Button) layout.findViewById(R.id.annulla_crea_evento);
        Button create = (Button) layout.findViewById(R.id.conferma_crea_evento);
        final EditText titolo = (EditText) layout.findViewById(R.id.titolo_nuovo_evento);
        final EditText testo = (EditText) layout.findViewById(R.id.testo_nuovo_evento);
        final EditText data = (EditText) layout.findViewById(R.id.data_nuovo_evento);
        final EditText luogo = (EditText) layout.findViewById(R.id.luogo_nuovo_evento);
        final int immagine = this.getContext().getResources().getIdentifier("careerfair_unitn", "drawable", this.getContext().getPackageName());
        final TextView Info = (TextView) layout.findViewById(R.id.info_nuovo_evento);

        builder.setView(layout);

        final AlertDialog alertDialog = builder.create();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(validate(titolo, testo, data, luogo, immagine, Info, db)) {
                        alertDialog.dismiss();
                        final Intent intent = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent);
                        Toast.makeText(view.getContext(), "Evento aggiunto correttamente", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("Errore add Evento", "Errore nell'aggiunta dell'evento");
                }
            }
        });

        alertDialog.show();
    }

    private boolean validate(EditText titolo, EditText testo, EditText data, EditText luogo, int immagine, TextView Info, DatabaseHelper db){
        String titoloS = titolo.getText().toString();
        String testoS = testo.getText().toString();
        String dataS = data.getText().toString();
        String luogoS = luogo.getText().toString();
        boolean check = true;
        int counter = 0;
        String info = "";

        if(titoloS == null || titoloS.equals("")){
            check = false;
            counter++;
            info = "Il titolo non sembra essere corretto";
            titolo.setBackgroundColor(Color.RED);
        }
        if(testoS == null || testoS.equals("")){
            check = false;
            counter++;
            info = "Il testo non sembra essere corretto";
            testo.setBackgroundColor(Color.RED);
        }
        if(dataS == null || dataS.equals("") || !checkDateFormat(dataS)){
            check = false;
            counter++;
            info = "La data non sembra essere corretta. Ricorda che un evento non può essere nel passato";
            data.setBackgroundColor(Color.RED);
        }
        if(luogoS == null || luogoS.equals("")){
            check = false;
            counter++;
            info = "Il luogo non sembra essere corretto";
            luogo.setBackgroundColor(Color.RED);
        }
        if(check){
            try {
                Evento e = new Evento(0, immagine, new ArrayList<Long>(), titoloS, testoS, luogoS, dataS);
                db.createEvento(e);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            if(counter > 1) {
                info = "Più parametri non sembrano essere corretti";
            }
            Info.setText(info);
            return false;
        }
    }

    public Boolean checkDateFormat(String date){
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = format.parse(date);
            if(d.after(new Date())) {
                return true;
            }else{
                return false;
            }
        }catch (ParseException e){
            return false;
        }
    }
}
