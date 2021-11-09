package com.example.myapplication.ui.asistencia_medica;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AsistenciaMedicaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AsistenciaMedicaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento de asistencia medica");
    }

    public LiveData<String> getText() {
        return mText;
    }
}