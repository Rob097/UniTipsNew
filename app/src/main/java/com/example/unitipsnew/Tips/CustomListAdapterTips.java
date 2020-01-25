package com.example.unitipsnew.Tips;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.unitipsnew.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListAdapterTips extends ArrayAdapter<Tip> {
    Context context;
    int resourses;
    List<Tip> tips;
    public static final String EXTRA_TIP = "idTip";

    public CustomListAdapterTips(Context context, int resourses, List<Tip> tips) {
        super(context, resourses, tips);

        this.context = context;
        this.resourses = resourses;
        this.tips = tips;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resourses, null);

        TextView titolo_tip = view.findViewById(R.id.titolo_tip);
        TextView testo_tip = view.findViewById(R.id.testo_tip);
        TextView like_tip = view.findViewById(R.id.like_tip);
        TextView dislike_tip = view.findViewById(R.id.dislike_tip);
        TextView commenti_tip = view.findViewById(R.id.commenti_tip);

        final Tip tip = tips.get(position);

        titolo_tip.setText(tip.getTitolo());
        testo_tip.setText(tip.getTesto());
        like_tip.setText(""+tip.getLike());
        dislike_tip.setText(""+tip.getDislike());
        commenti_tip.setText(""+tip.getCommenti().size());

        final Intent intent = new Intent(context, TipsActivity.class);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(EXTRA_TIP, "" + tip.getId());
                context.startActivity(intent);
            }
        });

        return view;
    }

}
