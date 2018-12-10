package shake.letz.wovent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import Objetos.Actividad;

public class DetalleEventoAdapter extends  RecyclerView.Adapter<DetalleEventoAdapter.ActividadHolder> {

    List<Actividad> actividades;

    public DetalleEventoAdapter(List<Actividad> actividades) {this.actividades = actividades;}

    public ActividadHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lista_programa, parent, false);
        ActividadHolder holder = new ActividadHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ActividadHolder actividadHolder, int position) {
        Actividad actividad = actividades.get(position);
        actividadHolder.textViewActividad.setText(actividad.getNombre());
        actividadHolder.textViewDescActividad.setText(actividad.getDescripcion());
        actividadHolder.textViewHora.setText(actividad.getHorario());
    }

    @Override
    public int getItemCount() {
        return actividades.size();
    }

    public class ActividadHolder extends  RecyclerView.ViewHolder {
        TextView textViewActividad;
        TextView textViewDescActividad;
        TextView textViewHora;

        public ActividadHolder(@NonNull View itemView) {
            super(itemView);

            textViewActividad = (TextView) itemView.findViewById(R.id.line_up);
            textViewDescActividad = (TextView) itemView.findViewById(R.id.desc_actividad);
            textViewHora = (TextView) itemView.findViewById(R.id.hora_actividad);

        }
    }




}
