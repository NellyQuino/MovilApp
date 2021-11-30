package com.example.myapplication.ui.bitacora;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.PrincipalActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Pregunta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Calendar;


public class BitacoraFragment extends Fragment {

    EditText pq1, pq2, pq3;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    TextView userName;
    Button bttn_save;



    private String[] headers = {"Pregunta","Respuesta"};
    private String shortText = "Tijuana, Baja California a";
    private String longText = "La información que contiene este documento es de carácter confidencial, solo entre paciente-doctor.";
    TemplatePDF templatePDF;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState ) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_bitacora, container, false);

        pq1 = root.findViewById(R.id.txt_qna1);
        pq2 = root.findViewById(R.id.txt_qna2);
        pq3 = root.findViewById(R.id.txt_qna3);
        databaseReference = LoginActivity.getDatabaseReference();

        bttn_save = root.findViewById(R.id.bttn_save);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        bttn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String qna1 = pq1.getText().toString();
                String qna2 = pq2.getText().toString();
                String qna3 = pq3.getText().toString();

                firebaseAuth = LoginActivity.getmAuth();
                currentuser = firebaseAuth.getCurrentUser();

                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String date = df.format(Calendar.getInstance().getTime());

                if (qna1.equals("") || qna2.equals("") || qna3.equals("")) {
                    validacion();
                } else {

                    Pregunta qna = new Pregunta();
                    qna.setUid(UUID.randomUUID().toString());
                    qna.setQ1(qna1);
                    qna.setQ2(qna2);
                    qna.setQ3(qna3);

                    databaseReference.child("Pregunta").child(qna.getUid()).setValue(qna);
                    Toast.makeText(PrincipalActivity.getContext(), "Registrado", Toast.LENGTH_LONG).show();

                    templatePDF = new TemplatePDF(getContext());
                    templatePDF.openDocument();
                    templatePDF.addMetaData("Bitácora","Análisis de estado emocinal ","MiBitácora");
                    templatePDF.addTitles("Mi bitácora", "Paciente" + " " + currentuser.getDisplayName());
                    templatePDF.addParagraph(shortText +" "+ date);
                    templatePDF.addParagraph(longText);
                    templatePDF.createTable(headers, getData());
                    templatePDF.closeDocument();
                    limparCajas();

                }

            }
        });
        return root;
    }
    private void limparCajas() {
        pq1.setText("");
        pq2.setText("");
        pq3.setText("");
    }

    private void validacion() {
        String qn1 = pq1.getText().toString();
        String qn2 = pq2.getText().toString();
        String qn3 = pq3.getText().toString();

        if (qn1.equals("")){
            pq1.setError("Requerido");
        }
        else if (qn2.equals("")) {
            pq2.setError("Requerido");
        }
        else if (qn3.equals("")) {
            pq3.setError("Requirido");
        }

    }
    private ArrayList<String[]> getData(){
        ArrayList<String[]> rows = new ArrayList<>();

        rows.add(new String[]{"¿Cómo te sientes?",pq1.getText().toString()});
        rows.add(new String[]{"¿Qué obstaculo le impide sentirse bien hoy?",pq2.getText().toString()});
        rows.add(new String[]{"¿Cuál fue el mejor momento que tuvo hoy?",pq3.getText().toString()});
        return rows;
    }





}