package com.example.myapplication.ui.bitacora;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BitacoraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BitacoraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento de bitacora");
    }

    public LiveData<String> getText() {
        return mText;
    }
}