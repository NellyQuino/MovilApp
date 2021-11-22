package com.example.myapplication;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;
import java.util.concurrent.Executor;

public class SignUpFragment extends Fragment {

    EditText name, email, password, confirmpass;
    Button signup;
    float v = 0;
    private static final String TAG = "EmailPassword";
    FirebaseAuth mAuth;
    Dialog signUpSucceful;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_fragment, container, false);

        mAuth = LoginActivity.getmAuth();

        name = root.findViewById(R.id.eTxt_nombre);
        email = root.findViewById(R.id.etxt_email);
        password = root.findViewById(R.id.eTxt_password);
        confirmpass = root.findViewById(R.id.eTxt_confirmpass);
        signup = root.findViewById(R.id.bttn_signup);

        name.setTranslationX(800);
        email.setTranslationX(800);
        password.setTranslationX(800);
        confirmpass.setTranslationX(800);
        signup.setTranslationX(800);

        name.setAlpha(v);
        email.setAlpha(v);
        password.setAlpha(v);
        confirmpass.setAlpha(v);
        signup.setAlpha(v);

        name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validacion();
            }
        });

        return root;
    }

    private void validacion(){
        String nombre = name.getText().toString();
        String correo = email.getText().toString();
        String contra = password.getText().toString();
        String confirmcontra = confirmpass.getText().toString();
        if(nombre.equals("")){
            name.setError("Obligatorio");
        }
        else if(correo.equals("")){
            email.setError("Obligatorio");
        }
        else if(contra.equals("")){
            password.setError("Obligatorio");
        }
        else if(confirmcontra.equals("")){
            confirmpass.setError("Obligatorio");
        }
        else if(!(confirmcontra.equals(contra))){
            confirmpass.setError("Las contraseñas no coinciden");
        }
        else{
            if(contra.length() >= 8){
                createAccount(correo, contra, nombre);
                //User user = new User();
                //user.setId(UUID.randomUUID().toString());
                //user.setName(name.getText().toString());
                //user.setEmail(email.getText().toString());
               // user.setPassword(password.getText().toString());
                //LoginActivity.getDatabaseReference().child("User").child(user.getId()).setValue(user);
               // LoginActivity.getDialog().show();
               // new CountDownTimer(2000, 1000){
                   // @Override
                    //public void onTick(long l) {
                   // }
                   // @Override
                   // public void onFinish() {
                        //LoginActivity.getDialog().dismiss();
                    //}
                //}.start();
                //limpiarEditTxt();
                //existeUsuario();
            }
            else{
                password.setError("La contraseña debe tener igual o mas de 8 caracteres");
            }
        }
    }

    private void createAccount(String email, String password, String name) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new LoginActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(LoginActivity.getContext(), "Registro exitoso!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates);
                            //signUpSucceful = LoginActivity.getDialog();
                            //signUpSucceful.setContentView(R.layout.custom_dialog);
                            //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                //signUpSucceful.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_bg));
                            //}
                            //signUpSucceful.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            //signUpSucceful.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            //signUpSucceful.show();
                            LoginActivity.getDialog().show();
                            new CountDownTimer(4000, 1000){
                                @Override
                                public void onTick(long l) { }
                                @Override
                                public void onFinish() {
                                    //signUpSucceful.dismiss();
                                    LoginActivity.getDialog().dismiss();
                                    Intent intent = new Intent(LoginActivity.getContext(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            }.start();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(LoginActivity.getContext(), "Ya existe un usuario\n con el correo ingresado!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(LoginActivity.getContext(), "Registro fallido, intente de nuevo.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void existeUsuario(){
        final boolean[] usuarioEncontrado = new boolean[1];
        final boolean[] correoEncontrado = new boolean[1];
        LoginActivity.getDatabaseReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    User user = objSnapshot.getValue(User.class);
                    if(user.name.equals(name.getText().toString())){
                        usuarioEncontrado[0] = true;
                    }
                    else{
                        usuarioEncontrado[0] = false;
                        if(user.email.equals(email.getText().toString())){
                            correoEncontrado[0] = true;
                        }
                        else {
                            correoEncontrado[0] = false;
                        }
                    }
                }
                if(usuarioEncontrado[0] == false){
                    if(correoEncontrado[0] == false){
                        //createAccount();
                    }
                    else{
                        email.setError("Correo ya registrado");
                    }
                }
                else{
                    name.setError("Usuario ya registrado");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void limpiarEditTxt() {
        name.setText("");
        email.setText("");
        password.setText("");
        confirmpass.setText("");
    }
}