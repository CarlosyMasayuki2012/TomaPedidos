package com.example.sistemas.tomapedidos.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sistemas.tomapedidos.Entidades.Productos;
import com.example.sistemas.tomapedidos.R;

import java.util.List;

public class BandejaProductosAdapter1 extends RecyclerView.Adapter<BandejaProductosAdapter1.ViewHolderDatos>  implements View.OnClickListener {


    // Adaptador que va a permitir hacer el enlace con de la lista con la vista en el layout

    List<Productos> listaproductos;  // Se Genera una lista de usuarios

    private View.OnClickListener listener;  //


    public BandejaProductosAdapter1(List<Productos> listaproductos) {
        this.listaproductos = listaproductos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_usuario,null);

        // Permite saber los parametros de layout que se va a mostrar en el RecylerView
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {

        viewHolderDatos.tvcodigonombreproducto.setText(listaproductos.get(i).getCodigo());
        viewHolderDatos.tvdescripcion.setText(listaproductos.get(i).getDescripcion());

    }


    @Override
    public int getItemCount() {
        return listaproductos.size();
    }

    @Override
    public void onClick(View v) {

        if (listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvcodigonombreproducto,tvdescripcion;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvcodigonombreproducto = itemView.findViewById(R.id.tvCodigoNombreProducto);
            tvdescripcion = itemView.findViewById(R.id.tvDescripcion);

        }
    }
    public void setOnClickListener(View.OnClickListener listener){

        this.listener = listener;

    }
}
