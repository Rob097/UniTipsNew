package com.example.unitipsnew.Eventi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
    public static final String EXTRA_EVENTO = "idEvento";

    public CustomListAdapterEventi(Context context, int resourses, List<Evento> eventi) {
        super(context, resourses, eventi);

        this.context = context;
        this.resourses = resourses;
        this.eventi = eventi;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resourses, null);

        TextView titolo_evento = view.findViewById(R.id.titolo_evento);
        ImageView immagine_evento = view.findViewById(R.id.immagine_evento);
        TextView luogo_evento = view.findViewById(R.id.luogo_evento);
        TextView data_evento = view.findViewById(R.id.data_evento);
        TextView interessati_evento = view.findViewById(R.id.interessati_evento);

        final Evento evento = eventi.get(position);

        titolo_evento.setText(evento.getTitolo());

        Bitmap img = stringToBitmap(evento.getImmagine());

        immagine_evento.setImageBitmap(img);
        luogo_evento.setText(evento.getLuogo());
        data_evento.setText(evento.getData());
        interessati_evento.setText(evento.getInteressati().size() + " interessati");

        final Intent intent = new Intent(context, EventiActivity.class);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(EXTRA_EVENTO, "" + evento.getId());
                context.startActivity(intent);
            }
        });

        return view;
    }

    private Bitmap stringToBitmap(String string){
        Bitmap bitmap = null;
        try{
            byte[] encodeByte = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);


        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }
}
