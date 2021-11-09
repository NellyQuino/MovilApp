package com.example.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.entity.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItem = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        SliderItem sliderItem = mSliderItem.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImagen())
                .fitCenter()
                .into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return mSliderItem.size();
    }

    public void updateItem(List<SliderItem> lista) {
        mSliderItem.clear();
        mSliderItem.addAll(lista);
        this.notifyDataSetChanged();
    }

    protected class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageView;
        TextView textView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_auto_image_slider);
            textView = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}