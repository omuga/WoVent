package shake.letz.wovent;

import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import Objetos.Evento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListaEventosAdapter extends  RecyclerView.Adapter<ListaEventosAdapter.EventoHolder> {

    List<Evento> eventos;

    public ListaEventosAdapter(List<Evento> eventos) {this.eventos = eventos;}

    public EventoHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_tiendas, parent, false);
        EventoHolder holder = new EventoHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventoHolder eventoHolder, int position) {
        Evento evento = eventos.get(position);
        eventoHolder.textViewEvento.setText(evento.getNombre());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class EventoHolder extends  RecyclerView.ViewHolder {
        TextView textViewEvento;

        public EventoHolder(@NonNull View itemView) {
            super(itemView);

            textViewEvento = (TextView) itemView.findViewById(R.id.textview_tiendas);
            textViewEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database;
                    DatabaseReference actividadRef;
                    Integer pos = getAdapterPosition();
                    Evento event = eventos.get(pos);
                    Toast.makeText(v.getContext(), event.getNombre(), Toast.LENGTH_SHORT).show();
                    database = FirebaseDatabase.getInstance();
                    actividadRef = database.getReference("Actividad");
                    actividadRef.push().setValue(event.getNombre());


                }
            });

        }
    }




}
