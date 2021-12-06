package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.ui.entity.ListElement;
import com.example.myapplication.ui.home.HomeFragment;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;



public class EjerciciosActivity extends AppCompatActivity {


    private FragmentManager mfragmentmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        mfragmentmanager = getSupportFragmentManager();
        ListElement actividad = (ListElement) getIntent().getSerializableExtra("ListElement");
        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding(actividad.getDescripcion()));

        FragmentTransaction fragmentTransaction = mfragmentmanager.beginTransaction();
        fragmentTransaction.add(R.id.ejercicios, paperOnboardingFragment);
        fragmentTransaction.commit();

    }
    private ArrayList<PaperOnboardingPage> getDataForOnBoarding(String descripcion) {
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        PaperOnboardingPage scr1,scr2,scr3,scr4;
        switch (descripcion){
            case "Por medio del cuello" :
                scr1 = new PaperOnboardingPage("Cuello","Paso 1: Acomodar tu cabeza del lado derecho ", Color.parseColor("#668974"),
                       R.drawable.cuello1,R.drawable.cuello);
                scr2 = new PaperOnboardingPage("Cuello","Paso 2: Hace lo mismo con el lado contrario", Color.parseColor("#668974"),
                        R.drawable.cuello2,R.drawable.cuello);
                elements.add(scr1);
                elements.add(scr2);
                break;
            case "Por medio de la espalda":
                scr1 = new PaperOnboardingPage("Espalda","Paso 1: Estar parado con los pies juntos", Color.parseColor("#668974"),
                        R.drawable.espalda1,R.drawable.espalda);
                scr2 = new PaperOnboardingPage("Espalda","Paso 2: Bajar hasta intentar tocar los pies sin inclinar las rodillas", Color.parseColor("#668974"),
                        R.drawable.espalda2,R.drawable.espalda);
                scr3 = new PaperOnboardingPage("Espalda","Paso 3: Recuperar la postura vertical sin forzar el cuello", Color.parseColor("#668974"),
                        R.drawable.espalda3,R.drawable.espalda);
                elements.add(scr1);
                elements.add(scr2);
                elements.add(scr3);
                break;
            case "Por medio de nuca":
                scr1 = new PaperOnboardingPage("Nuca","Paso 1: Estirar el brazo de forma horizontal", Color.parseColor("#668974"),
                        R.drawable.nuca1,R.drawable.cabeza);
                scr2 = new PaperOnboardingPage("Nuca","Paso 2: Agarrar la parte de la nuca y bajar lo mas que se pueda. Repetir este paso del lado contrario", Color.parseColor("#668974"),
                        R.drawable.remplazo2,R.drawable.cabeza);
                elements.add(scr1);
                elements.add(scr2);
                break;
            case "Por medio de las manos":
                scr1 = new PaperOnboardingPage("Muñeca","Paso 1: Tener brazo derecho en forma horizontal con la palma levanta y el brazo izquierdo jalando ligeramente", Color.parseColor("#668974"),
                        R.drawable.muneca1,R.drawable.mano);
                scr2 = new PaperOnboardingPage("Muñeca","Paso 2: Ahora hacerlo con la palma hacia abajo", Color.parseColor("#668974"),
                        R.drawable.muneca2,R.drawable.mano);
                scr3 = new PaperOnboardingPage("Muñeca","Paso 3: Ahora estirar el brazo izquierdo y con la palma levantada", Color.parseColor("#668974"),
                        R.drawable.muneca3,R.drawable.mano);
                scr4 = new PaperOnboardingPage("Muñeca","Paso 4: Ahora la palma sería hacía abajo", Color.parseColor("#668974"),
                        R.drawable.remplazo1,R.drawable.mano);
                elements.add(scr1);
                elements.add(scr2);
                elements.add(scr3);
                elements.add(scr4);
                break;
            case "Por medio de las piernas":
                scr1 = new PaperOnboardingPage("Piernas","Paso 1: Estar sentados en una posición firme", Color.parseColor("#668974"),
                        R.drawable.remplazo3,R.drawable.piernas);
                scr2 = new PaperOnboardingPage("Piernas","Paso 2: Levantar la pierna izquierda lo mas firme posible y empezar a mover el pie de arriba hacia abajo ", Color.parseColor("#668974"),
                        R.drawable.pierna2,R.drawable.piernas);
                scr3 = new PaperOnboardingPage("Piernas","Paso 3: Repetir el paso anterior pero con la pierna contraria", Color.parseColor("#668974"),
                        R.drawable.pierna3,R.drawable.piernas);
                elements.add(scr1);
                elements.add(scr2);
                elements.add(scr3);
                break;
        }

        return elements;
    }
}