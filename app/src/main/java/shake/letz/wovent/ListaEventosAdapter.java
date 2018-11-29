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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import Objetos.Evento;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

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
        ImageView eliminar_evento;
        TextView textViewEditEvento;
        public EventoHolder(@NonNull View itemView) {
            super(itemView);
            textViewEvento = (TextView) itemView.findViewById(R.id.textview_tiendas);
            eliminar_evento = itemView.findViewById(R.id.trash);
            textViewEditEvento = itemView.findViewById(R.id.edit_evento);
            textViewEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database;
                    DatabaseReference actividadRef;
                    Integer pos = getAdapterPosition();
                    Evento event = eventos.get(pos);
                    Toast.makeText(v.getContext(), event.getNombre(), Toast.LENGTH_SHORT).show();
                    database = FirebaseDatabase.getInstance();
                    Intent intent = new Intent(v.getContext(),AgregarActividadActivity.class);
                    intent.putExtra("evento",event.getNombre());
                    v.getContext().startActivity(intent);
                }
            });
            eliminar_evento.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer pos = getAdapterPosition();
                    Evento evento = eventos.get(pos);
                    DatabaseReference EventoRef = FirebaseDatabase.getInstance().getReference();
                    Query eventQuery = EventoRef.child("Evento").orderByChild("nombre").equalTo(evento.getNombre());
                    eventQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()){
                                eventSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });
                }
            }));
            textViewEditEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DatabaseReference database_edicion = FirebaseDatabase.getInstance().getReference("Tienda");
                    Integer pos = getAdapterPosition();
                    //Tienda tiendap = tiendas.get(pos);
                    //int id_tienda = tiendap.getId();
                    Intent intent = new Intent((v.getContext()),EditarEventoActivity.class);
                    //intent.putExtra("tienda",id_tienda);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }




}
