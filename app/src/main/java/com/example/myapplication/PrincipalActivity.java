package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PrincipalActivity extends AppCompatActivity {

    private static Context context;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    TextView userName;
    ImageButton userMenu;
    Button editAccount, signOut;
    TextView userInfo;
    Dialog menuDialog;


    public static Context getContext() {
        return context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        context = PrincipalActivity.this;

        firebaseAuth = LoginActivity.getmAuth();
        //currentuser = LoginActivity.getmAuth().getCurrentUser();
        currentuser = firebaseAuth.getCurrentUser();
        userName = findViewById(R.id.txtView_nombre);
        userName.setText(currentuser.getDisplayName());
        userMenu = findViewById(R.id.ibttn_user);
        context = PrincipalActivity.this;

        menuDialog = new Dialog(this);
        menuDialog.setContentView(R.layout.options_account);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            menuDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }
        menuDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        userInfo = menuDialog.findViewById(R.id.txtView_userdialog);
        userInfo.setText(currentuser.getEmail());
        editAccount = menuDialog.findViewById(R.id.bttn_editaccount);
        signOut = menuDialog.findViewById(R.id.bttn_signout);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); esta linea de codigo fue un fastidio dar con ella
        NavigationUI.setupWithNavController(navView, navController);

        userMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDialog.show();
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(PrincipalActivity.this, "Activity_EditarCuenta_InProgress", Toast.LENGTH_SHORT).show();
                menuDialog.dismiss();
                showDialog();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(PrincipalActivity.this, "saliendo", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_account);

        EditText editNombre = dialog.findViewById(R.id.edit_Nombre);
        EditText editPass = dialog.findViewById(R.id.edit_password);
        ImageButton bttnEditNombre = dialog.findViewById(R.id.bttn_editNombre);
        ImageButton bttnEditPass = dialog.findViewById(R.id.bttn_editPass);
        Button bttnGuardarCambios = dialog.findViewById(R.id.bttn_guardarCambios);

        editNombre.setText(currentuser.getDisplayName().toString());

        bttnEditNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editNombre.isEnabled()){
                    bttnEditNombre.setImageResource(R.drawable.check);
                    editNombre.setEnabled(true);
                }
                else {
                    bttnEditNombre.setImageResource(R.drawable.editar);
                    editNombre.setEnabled(false);
                }
            }
        });

        bttnEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editPass.isEnabled()){
                    bttnEditPass.setImageResource(R.drawable.check);
                    editPass.setEnabled(true);
                }
                else {
                    bttnEditPass.setImageResource(R.drawable.editar);
                    editPass.setEnabled(false);
                }
            }
        });

        bttnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUpdate = editNombre.getText().toString();
                String passUpdate = editPass.getText().toString();
                if(!editNombre.isEnabled() && !editPass.isEnabled()) {
                    if(!nameUpdate.isEmpty() && !nameUpdate.equals(currentuser.getDisplayName().trim())){
                        if(nameUpdate.length() > 4){
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameUpdate)
                                    .build();
                            currentuser.updateProfile(profileUpdates);
                            Toast.makeText(PrincipalActivity.this,"Se ha cambiado el nombre exitosamente!", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(PrincipalActivity.this, SplashScreenActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(PrincipalActivity.this,"Nombre demasiado corto", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(!passUpdate.isEmpty()) {
                        if(passUpdate.length() > 7) {
                            currentuser.updatePassword(passUpdate);
                            Toast.makeText(PrincipalActivity.this,"Se ha cambiado la contraseña exitosamente!", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            finish();
                            Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(PrincipalActivity.this,"Contraseña demasiada corta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(PrincipalActivity.this,"Debes terminar de editar los datos para poder guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}