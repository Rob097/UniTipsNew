package com.example.unitipsnew.Tips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.unitipsnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class Tab3Tips extends Fragment {

    List<Tip> tips;
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3tips, container, false);

        tips = new ArrayList<>();
        String testoProva = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat";

        tips.add(new Tip(1, "Titolo Tip 1", testoProva, 1, 5, 1));
        tips.add(new Tip(2, "Titolo Tip 2", testoProva, 2, 4, 2));
        tips.add(new Tip(3, "Titolo Tip 3", testoProva, 3, 3, 3));
        tips.add(new Tip(4, "Titolo Tip 4", testoProva, 4, 2, 4));
        tips.add(new Tip(5, "Titolo Tip 5", testoProva, 5, 1, 5));

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
