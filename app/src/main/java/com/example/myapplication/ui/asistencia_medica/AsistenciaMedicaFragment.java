package com.example.myapplication.ui.asistencia_medica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAsistenciaBinding;

public class AsistenciaMedicaFragment extends Fragment {

    Button bttn_asistecia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState ) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_asistencia, container, false);

        bttn_asistecia = root.findViewById(R.id.bttn_asistecia);


        return root;
    }

}