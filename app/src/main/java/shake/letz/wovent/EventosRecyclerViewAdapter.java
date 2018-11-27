package shake.letz.wovent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Objetos.Evento;

public class EventosRecyclerViewAdapter extends RecyclerView.Adapter<EventosRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {

    private ArrayList<Evento> mEventos;
    Context mContext;
    private EventosFragment.OnFragmentInteractionListener mListener;
    private View.OnClickListener listener;

    public EventosRecyclerViewAdapter(ArrayList<Evento> eventos, Context mContext) {
        this.mEventos = eventos;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_eventos, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.t_name.setText(mEventos.get(position).getNombre());
        holder.t_status.setText("");
        Glide.with(mContext).load(mEventos.get(position).getUri()).into(holder.t_img);
    }

    @Override
    public int getItemCount() {
        return mEventos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView t_name;
        public final TextView t_status;
        public ImageView t_img;

        public ViewHolder(View view) {
            super(view);
            //mView = view;

            t_name = (TextView) view.findViewById(R.id.t_name);
            t_status = (TextView) view.findViewById(R.id.t_status);
            t_img = (ImageView) itemView.findViewById(R.id.miniTienda);
        }
    }


}
