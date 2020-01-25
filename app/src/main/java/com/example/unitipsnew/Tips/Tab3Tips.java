package com.example.unitipsnew.Tips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

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
                Snackbar.make(view, "Replace with your own action of Tips", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return rootView;
    }
}
