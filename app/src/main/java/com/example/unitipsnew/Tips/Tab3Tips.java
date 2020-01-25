package com.example.unitipsnew.Tips;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.unitipsnew.Utente.Utente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Tab3Tips extends Fragment {

    List<Tip> tips;
    ListView listview;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3tips, container, false);

        db = new DatabaseHelper(getActivity());
        tips = db.getAllTips();

        listview = (ListView) rootView.findViewById(R.id.listviewTips);
        CustomListAdapterTips adapterTips = new CustomListAdapterTips(this.getContext(), R.layout.list_item_tips, tips);
        listview.setAdapter(adapterTips);

        FloatingActionButton fab = rootView.findViewById(R.id.fab_add_tip);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewTip();
            }
        });
        return rootView;
    }

    private void openNewTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.new_tip_form, null);

        Button cancel = (Button) layout.findViewById(R.id.button_cancel_tip);
        Button create = (Button) layout.findViewById(R.id.button_add_tip);
        final EditText titolo = (EditText) layout.findViewById(R.id.add_tip_title);
        final EditText testo = (EditText) layout.findViewById(R.id.add_tip_text);
        final TextView Info = (TextView) layout.findViewById(R.id.info_nuovo_tip);

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

                if(titolo.getText().toString() != null && testo.getText().toString() != null && !titolo.getText().toString().equals("") && !testo.getText().toString().equals("")){

                    try{

                        final Tip t = new Tip(titolo.getText().toString(), testo.getText().toString());
                        db.createTip(t);
                        alertDialog.dismiss();
                        final Intent intent = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent);
                        Toast.makeText(view.getContext(), "Tip aggiunto correttamente", Toast.LENGTH_SHORT).show();

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }else{
                    Info.setText("Il titolo e/o il testo non sono corretti");
                }
            }
        });

        alertDialog.show();
    }
}
