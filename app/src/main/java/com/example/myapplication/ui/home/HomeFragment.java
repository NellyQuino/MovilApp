package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.EjerciciosActivity;
import com.example.myapplication.PrincipalActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.adapter.ListAdapter;
import com.example.myapplication.ui.adapter.SliderAdapter;
import com.example.myapplication.ui.entity.ListElement;
import com.example.myapplication.ui.entity.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SliderView svCarrusel;
    private SliderAdapter sliderAdapter;
    private List<ListElement> elements;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false) ;
        init(root);

        return root;
    }

    private void init(@NonNull ViewGroup root) {
        elements = new ArrayList<>();
        elements.add(new ListElement(R.drawable.cuello, "#D2DCD6","Control de estrés", "Por medio del cuello"));
        elements.add(new ListElement(R.drawable.espalda,"#D2DCD6", "Control de ansiedad", "Por medio de la espalda"));
        elements.add(new ListElement(R.drawable.cabeza,"#D2DCD6", "Control de estrés", "Por medio de nuca"));
        elements.add(new ListElement(R.drawable.mano,"#D2DCD6", "Control de ansiedad", "Por medio de las manos"));
        elements.add(new ListElement(R.drawable.piernas,"#D2DCD6", "Control de estrés", "Por medio de las piernas"));

        ListAdapter listAdapter = new ListAdapter(elements, getContext(), new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                moveToActivity(item);
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToActivity(ListElement item) {
        Intent intent = new Intent(getContext(), EjerciciosActivity.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }

    private void init(View view) {
        svCarrusel = view.findViewById(R.id.svCarrusel);
    }

    private void initAdapter() {
        sliderAdapter = new SliderAdapter(getContext());

        svCarrusel.setSliderAdapter(sliderAdapter);

        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM);
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarrusel.setIndicatorSelectedColor(Color.WHITE);
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY);
        svCarrusel.setScrollTimeInSec(4);
        svCarrusel.startAutoCycle();
    }

    private void loadData() {
        List<SliderItem> lista = new ArrayList<>();
        lista.add(new SliderItem(R.drawable.medicina4));
        lista.add(new SliderItem(R.drawable.medicina5));
        lista.add(new SliderItem(R.drawable.medicina6));
        lista.add(new SliderItem(R.drawable.medicina7));
        lista.add(new SliderItem(R.drawable.medicina8));
        sliderAdapter.updateItem(lista);
    }
}