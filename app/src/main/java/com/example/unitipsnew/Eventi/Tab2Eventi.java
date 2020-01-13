package com.example.unitipsnew.Eventi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.unitipsnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;

public class Tab2Eventi extends Fragment {

    List<Evento> eventi;
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2eventi, container, false);

        eventi = new ArrayList<>();

        eventi.add(new Evento("Festa Universitaria Centrale", "Trento", new Timestamp(new Date().getTime()), R.drawable.fuc_unitn));
        eventi.add(new Evento("Career Fair", "Trento", new Timestamp(new Date().getTime()), R.drawable.careerfair_unitn));
        eventi.add(new Evento("Festa Universitaria Centrale", "Trento", new Timestamp(new Date().getTime()), R.drawable.fuc_unitn));
        eventi.add(new Evento("Career Fair", "Trento", new Timestamp(new Date().getTime()), R.drawable.careerfair_unitn));

        listview = (ListView) rootView.findViewById(R.id.listviewEventi);
        CustomListAdapterEventi adapterEventi = new CustomListAdapterEventi(this.getContext(), R.layout.list_item_eventi, eventi);
        listview.setAdapter(adapterEventi);

        FloatingActionButton fab = rootView.findViewById(R.id.fab_filter_eventi);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action of Eventi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return rootView;
    }
}
