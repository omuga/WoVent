package shake.letz.wovent;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Objetos.Actividad;

public class DetalleEvento extends AppCompatActivity {
    private TextView nombreEvento;
    private ImageView imagenEvento;
    private TextView fechasEvento;
    private TextView fechaeEvento;
    private TextView descEvento;
    private DatabaseReference mDatabase;
    Context context = this;

    RecyclerView rv;
    public ArrayList<Actividad> actividades;
    DetalleEventoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalle_eventos);

        Intent myIntent = getIntent();
        final String keynombre = myIntent.getStringExtra("keynombre");
        String keyuri = myIntent.getStringExtra("keyuri");
        String fechas = myIntent.getStringExtra("keyfechas");
        String fechae = myIntent.getStringExtra("keyfechae");
        String descripcion = myIntent.getStringExtra("keydescripcion");

        nombreEvento = findViewById(R.id.nombre_evento);
        imagenEvento = findViewById(R.id.image_event);
        fechasEvento = findViewById(R.id.start_date);
        fechaeEvento = findViewById(R.id.end_date);
        descEvento = findViewById(R.id.desc_event);

        nombreEvento.setText(keynombre);
        fechasEvento.setText(fechas);
        fechaeEvento.setText(fechae);
        descEvento.setText(descripcion);
        Glide.with(this)
                .load(keyuri)
                .into(imagenEvento);

        actividades = new ArrayList<>();
        rv = findViewById(R.id.recycler_actividades);
        adapter = new DetalleEventoAdapter(actividades);
        mDatabase = FirebaseDatabase.getInstance().getReference("Actividad");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        final Actividad act = ds.getValue(Actividad.class);
                        if(act.getEvento().equals(keynombre)){
                            Log.d("DetalleEvento",act.getNombre());
                            actividades.add(act);
                        }
                    }
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(context));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
