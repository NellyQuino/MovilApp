package com.example.myapplication.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.entity.ListElement;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> dataList;
    private LayoutInflater inflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListElement item);
    }

    public ListAdapter(List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.dataList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void setItems(List<ListElement> items) {
        dataList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iImage;
        TextView titulo, descripcion;

        ViewHolder(View itemView) {
            super(itemView);
            iImage = itemView.findViewById(R.id.iImageItem);
            titulo = itemView.findViewById(R.id.tv_titulo);
            descripcion = itemView.findViewById(R.id.tv_descripcion);
        }

        void bindData(final ListElement item) {
            iImage.setImageResource(item.getImagen());
            iImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            titulo.setText(item.getTitulo());
            descripcion.setText(item.getDescripcion());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
