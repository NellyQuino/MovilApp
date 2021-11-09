package com.example.myapplication;

import static android.widget.Toast.makeText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class LoginFragment extends Fragment {

    EditText email, password;
    Button forgetPass, login;
    float v = 0;
    private static final String TAG = "EmailPassword";
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);

        mAuth = LoginActivity.getmAuth();

        email = root.findViewById(R.id.etxt_email);
        password = root.findViewById(R.id.eTxt_password);
        forgetPass = root.findViewById(R.id.bttn_forgetPass);
        login = root.findViewById(R.id.bttn_login);

        email.setTranslationX(800);
        password.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")){
                    email.setError("Obligatorio");
                }
                else if(password.getText().toString().equals("")){
                    password.setError("Obligatorio");
                }
                else
                {
                    signIn(email.getText().toString(), password.getText().toString());
                }
            }
        });

        return root;
    }

    private void validacion(){
        final boolean[] correoEncontrado = new boolean[1];
        final boolean[] contraseñaCorrecta = new boolean[1];
        LoginActivity.getDatabaseReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    User user = objSnapshot.getValue(User.class);
                    if(user.email.toString().equals(email.getText().toString())){
                        correoEncontrado[0] = true;
                        if(user.password.toString().equals((password.getText().toString()))){
                            contraseñaCorrecta[0] = true;
                        }
                        else {
                            contraseñaCorrecta[0] = false;
                        }
                    }
                    else{
                        correoEncontrado[0] = false;
                    }
                }
                if(correoEncontrado[0] == true){
                    if(contraseñaCorrecta[0] == true){
                        Intent intent = new Intent(LoginActivity.getContext(), PrincipalActivity.class);
                        startActivity(intent);
                    }
                    else{
                        password.setError("Contraseña incorrecta");
                    }
                }
                else email.setError("Correo no registrado");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new LoginActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.getContext(), PrincipalActivity.class);
                            startActivity(intent);
                        } else {
                            Context context = LoginActivity.getContext();
                            if(task.getException() instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(context, "El correo electronico ingresado\n no esta registrado",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(context, "La contraseña ingresada es incorrecta",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(context, "Inicio de sesión fallido, intente de nuevo",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void limpiarCajas(){
        email.setText("");
        password.setText("");
    }
}
