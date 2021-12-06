package com.example.myapplication.ui.asistencia_medica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.PrincipalActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAsistenciaBinding;

public class AsistenciaMedicaFragment extends Fragment {

    Button bttn_asistecia;
    Button bttn_hospital;
    private static final String TAG="Etiqueta: ";
    String phoneNumber = "6642937735";
    private static final Integer REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState ) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_asistencia, container, false);

        bttn_asistecia = root.findViewById(R.id.bttn_asistecia);

        //Llamarda
        bttn_asistecia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent llamadaIntent = new Intent(Intent.ACTION_CALL);
                llamadaIntent.setData(Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(llamadaIntent);
                } else {
                    /* Muestra el mensaje para dar permisos. */
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
                }
            }
        });
        bttn_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent llamadaIntent = new Intent(Intent.ACTION_CALL);
                llamadaIntent.setData(Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(llamadaIntent);
                } else {
                    /* Muestra el mensaje para dar permisos. */
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
                }
            }
        });
        return root;
    }

}