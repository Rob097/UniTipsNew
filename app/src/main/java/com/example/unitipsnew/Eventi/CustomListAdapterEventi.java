package com.example.unitipsnew.Eventi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.unitipsnew.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListAdapterEventi extends ArrayAdapter<Evento> {
    Context context;
    int resourses;
    List<Evento> eventi;

    public CustomListAdapterEventi(Context context, int resourses, List<Evento> eventi) {
        super(context, resourses, eventi);

        this.context = context;
        this.resourses = resourses;
        this.eventi = eventi;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resourses, null);

        TextView titolo_evento = view.findViewById(R.id.titolo_evento);
        ImageView immagine_evento = view.findViewById(R.id.immagine_evento);
        TextView luogo_evento = view.findViewById(R.id.luogo_evento);
        TextView data_evento = view.findViewById(R.id.data_evento);

        Evento evento = eventi.get(position);

        titolo_evento.setText(evento.getTitolo());
        immagine_evento.setImageDrawable(context.getResources().getDrawable(evento.getImmagine()));
        luogo_evento.setText(evento.getLuogo());
        data_evento.setText(evento.getData().toString());

        return view;
    }
}
