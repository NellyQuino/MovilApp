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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                Toast.makeText(PrincipalActivity.this, "Activity_EditarCuenta_InProgress", Toast.LENGTH_SHORT).show();
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


}