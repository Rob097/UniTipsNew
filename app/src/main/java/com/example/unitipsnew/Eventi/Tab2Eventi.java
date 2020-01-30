package com.example.unitipsnew.Eventi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitipsnew.DatabaseHelper;
import com.example.unitipsnew.MainActivity;
import com.example.unitipsnew.R;
import com.example.unitipsnew.Recensioni.Recensione;
import com.example.unitipsnew.Utente.RegisterActivity;
import com.example.unitipsnew.allEventsMap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Tab2Eventi extends Fragment {

    List<Evento> eventi;
    ListView listview;
    DatabaseHelper db;
    ImageView img_evento;
    Bitmap img_bit;
    ProgressBar progress;
    private TextView Info;
    boolean check = false;
    Date data = new Date();

    final static int GALLERY_REQUEST = 100;
    final static int CAMERA_REQUEST = 101;
    private final static int PREFERED_WIDTH = 250;
    private final static int PREFERED_HEIGHT = 250;

    Calendar c;
    DatePickerDialog d;


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

        final Button mappa = (Button) rootView.findViewById(R.id.button_all_map);

        mappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mappa.setBackgroundColor(Color.GRAY);
                final Intent intent = new Intent(view.getContext(), allEventsMap.class);
                view.getContext().startActivity(intent);
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
                dialog.dismiss();
                openFiltra();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openFiltra() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        LayoutInflater inflater1 = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.filtra_eventi, null);

        final TextView giorno = (TextView) layout.findViewById(R.id.giorno_choose);
        final TextView settimana = (TextView) layout.findViewById(R.id.settimana_choose);
        final TextView mese = (TextView) layout.findViewById(R.id.mese_choose);
        final Button annulla = (Button) layout.findViewById(R.id.annulla_filter_corsi);

        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        giorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
                filtraCalendar("giorno");
            }
        });
        settimana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
                filtraCalendar("settimana");
            }
        });
        mese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
                filtraCalendar("mese");
            }
        });


        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
    }

    private void filtraCalendar(final String tipo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        final LayoutInflater inflater1 = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.eventi_giorno, null);

        final CalendarView calendario = (CalendarView) layout.findViewById(R.id.calendarView_eventi);
        final Button aggiorna = (Button) layout.findViewById(R.id.aggiorna_filter_eventi);
        final Button annulla = (Button) layout.findViewById(R.id.annulla_filter_eventi);
        TextView titolo = (TextView) layout.findViewById(R.id.titolo_calendar);
        switch (tipo) {
            case ("giorno"):
                titolo.setText("Scegli un giorno");
                break;
            case ("settimana"):
                titolo.setText("Scegli un giorno nella settimana che ti interessa");
                break;
            case ("mese"):
                titolo.setText("Scegli un giorno nel mese che ti interessa");
                break;
        }

        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                data = new GregorianCalendar(year, month, dayOfMonth).getTime();
            }
        });

        aggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Evento> eventi = new ArrayList<>();

                switch (tipo) {
                    case ("giorno"):
                        eventi = db.getAllEventiOfDay(data);
                        break;
                    case ("settimana"):
                        eventi = db.getAllEventiOfweek(data);
                        break;
                    case ("mese"):
                        eventi = db.getAllEventiOfMonth(data);
                        break;
                }

                openResults(eventi, tipo);
                alertDialog.hide();
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
    }


    private void openResults(List<Evento> eventi, String tipo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterMont1h = new SimpleDateFormat("MMMM yyyy");
        DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MMMM yyyy").withLocale(Locale.ITALIAN);
        //Create a custom layout for the dialog box
        final LayoutInflater inflater1 = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.tab2eventi, null);

        ListView listview1 = (ListView) layout.findViewById(R.id.listviewEventi);
        CustomListAdapterEventi adapter = new CustomListAdapterEventi(layout.getContext(), R.layout.list_item_eventi, eventi);
        listview1.setAdapter(adapter);
        FloatingActionButton fab = layout.findViewById(R.id.fab_filter_eventi);
        fab.hide();
        Button mappa = (Button) layout.findViewById(R.id.button_all_map);
        mappa.setVisibility(GONE);

        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        switch (tipo) {
            case ("giorno"):
                alertDialog.setTitle(formatter.format(data));
                break;
            case ("settimana"):
                alertDialog.setTitle("Settimana del " + formatter.format(data));
                break;
            case ("mese"):
                LocalDate d = data.toInstant().atZone(ZoneId.of("Europe/Rome")).toLocalDate();
                String initial = ""+(d.format(formatterMonth)).charAt(0);
                String rest = d.format(formatterMonth).substring(1);
                alertDialog.setTitle(initial.toUpperCase()+rest.toLowerCase());
                break;
        }

        if (eventi.isEmpty()) {
            alertDialog.setMessage("Nessun evento per questa data");
        }

        alertDialog.show();
    }

    private void openNewEvento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.new_evento_form, null);

        Button cancel = (Button) layout.findViewById(R.id.annulla_crea_evento);
        final Button create = (Button) layout.findViewById(R.id.conferma_crea_evento);
        progress = (ProgressBar) layout.findViewById(R.id.progressBar);
        final EditText titolo = (EditText) layout.findViewById(R.id.titolo_nuovo_evento);
        final EditText testo = (EditText) layout.findViewById(R.id.testo_nuovo_evento);
        final TextView data = (TextView) layout.findViewById(R.id.data_nuovo_evento);
        final EditText luogo = (EditText) layout.findViewById(R.id.luogo_nuovo_evento);
        final int immagine = this.getContext().getResources().getIdentifier("careerfair_unitn", "drawable", this.getContext().getPackageName());
        Info = (TextView) layout.findViewById(R.id.info_nuovo_evento);
        final ImageView immagine_evento = (ImageView) layout.findViewById(R.id.immagine_nuovo_evento);
        img_evento = (ImageView) layout.findViewById(R.id.immagine_nuovo_evento);
        progress.setIndeterminate(true);
        progress.setVisibility(GONE);
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
            public void onClick(final View view) {
                progress.setVisibility(VISIBLE);
                check = false;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (validate(titolo, testo, data, luogo, immagine_evento, Info, db)) {
                                check = true;
                                alertDialog.dismiss();
                                final Intent intent = new Intent(view.getContext(), MainActivity.class);
                                view.getContext().startActivity(intent);
                                Toast.makeText(view.getContext(), "Evento aggiunto correttamente", Toast.LENGTH_SHORT).show();
                            } else {
                                progress.setVisibility(GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Errore add Evento", "Errore nell'aggiunta dell'evento");
                        }
                    }
                }).start();
            }
        });

        img_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        Calendar cal;

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = c.getInstance();
                int year = cal.get(Calendar.YEAR);
                final int month = cal.get(Calendar.MONTH);
                final int giorno = cal.get(Calendar.DAY_OF_MONTH);

                d = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {

                        int m = mmonth + 1;
                        int g = mdayOfMonth;
                        String mS = "", gS = "";

                        if (g >= 1 && g <= 9) {
                            gS = "0" + g;
                        } else if (g > 9) {
                            gS = "" + g;
                        }
                        if (m >= 1 && m <= 9) {
                            mS = "0" + m;
                        } else if (m >= 10) {
                            mS = "" + m;
                        }

                        data.setText(gS + "/" + mS + "/" + myear);
                    }
                }, year, month, giorno);
                d.show();
            }
        });

        alertDialog.show();
    }

    private boolean validate(EditText titolo, EditText testo, TextView data, EditText
            luogo, ImageView immagine, TextView Info, DatabaseHelper db) {

        String titoloS = titolo.getText().toString();
        String testoS = testo.getText().toString();
        String dataS = data.getText().toString();
        String luogoS = luogo.getText().toString();

        boolean check = true;
        int counter = 0;
        String info = "";


        if (titoloS == null || titoloS.equals("")) {
            check = false;
            counter++;
            info = "Il titolo non sembra essere corretto";
            titolo.setBackgroundColor(Color.RED);
        }
        if (testoS == null || testoS.equals("")) {
            check = false;
            counter++;
            info = "Il testo non sembra essere corretto";
            testo.setBackgroundColor(Color.RED);
        }
        if (dataS == null || dataS.equals("") || !checkDateFormat(dataS)) {
            check = false;
            counter++;
            info = "La data non sembra essere corretta. Ricorda che un evento non può essere nel passato";
            data.setBackgroundColor(Color.RED);
        }
        if (luogoS == null || luogoS.equals("")) {
            check = false;
            counter++;
            info = "Il luogo non sembra essere corretto";
            luogo.setBackgroundColor(Color.RED);
        }
        if (img_bit != null && bitmapToString(img_bit).equals("")) {
            check = false;
            counter++;
            info = "L'immagine scelta è troppo pesante, il limite è 1 MB";
        }
        if (check) {
            try {

                int fattore;
                if (img_bit.getWidth() * img_bit.getHeight() < 2100000) {
                    fattore = 2;
                } else if (img_bit.getWidth() * img_bit.getHeight() > 2100000 && img_bit.getWidth() * img_bit.getHeight() < 24500000) {
                    fattore = 10;
                } else {
                    fattore = 20;
                }
                Bitmap resizedNewImage = getResizedBitmap(img_bit, img_bit.getWidth() / fattore, img_bit.getHeight() / fattore);
                String s = bitmapToString(resizedNewImage);
                Evento e = new Evento(0, s, new ArrayList<Long>(), titoloS, testoS, luogoS, dataS);
                db.createEvento(e);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            if (counter > 1) {
                info = "Più parametri non sembrano essere corretti";
            }
            Info.setText(info);
            return false;
        }
    }

    public Boolean checkDateFormat(String date) {
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = format.parse(date);
            if (d.after(new Date())) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        long lengthbmp = b.length;
        /*if(lengthbmp / 1000000 >= 3.8){
            return "";
        }*/
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    public void openGallery() {
        final CharSequence[] options = {"Scatta una foto", "Scegli dalla Galleria", "Annulla"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Tab2Eventi.this.getContext());
        builder.setTitle("Aggiungi un'immagine!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Scatta una foto")) {
                    openCamera();
                } else if (options[item].equals("Scegli dalla Galleria")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Seleziona immagine"), GALLERY_REQUEST);
                } else if (options[item].equals("Annulla")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                img_bit = BitmapFactory.decodeStream(imageStream);
                img_evento.setImageBitmap(img_bit);

            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                Bundle extras = data.getExtras();
                Bitmap image = (Bitmap) extras.get("data");
                img_bit = image;
                img_evento.setImageBitmap(image);

            } catch (Exception io) {

            }
        }
    }

    public Bitmap resizeImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = 250 / width;
        float scaleHeight = 250 / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resize = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        resize.recycle();
        return resize;

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
