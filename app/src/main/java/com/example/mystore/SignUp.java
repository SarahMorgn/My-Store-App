package com.example.mystore;

import static com.example.mystore.LogIn.releaseHomePage;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mystore.auths.UserHome;
import com.example.mystore.classes.LoadingDialog;
import com.example.mystore.classes.User;
import com.example.mystore.firestore.AuthRole;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import es.dmoral.toasty.Toasty;

;

public class SignUp extends AppCompatActivity {
    private static final String EMAIL = "EMAIL";
    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";
Button signUp;
FirebaseAuth auth;

EditText emailEditText,passwordEditText,nameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth=FirebaseAuth.getInstance();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailEditText=(EditText) findViewById(R.id.forgotPasswordByEmail);
        passwordEditText=(EditText) findViewById(R.id.logInPassword);
        nameEditText=findViewById(R.id.editTextName);

        if(savedInstanceState!=null){
            emailEditText.setText(savedInstanceState.getString(EMAIL));
            nameEditText.setText(savedInstanceState.getString(NAME));
            passwordEditText.setText(savedInstanceState.getString(PASSWORD));
        }


        signUp=findViewById(R.id.reset);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidEmail(emailEditText.getText().toString())&&
                        passwordEditText.getText().toString().length()>=6){

                    createUser(emailEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            nameEditText.getText().toString());

                }else if(!isValidEmail(emailEditText.getText().toString())){
                    Toasty.warning(SignUp.this,getString(R.string.invalid_email),Toast.LENGTH_LONG).show();
                }else {
                    Toasty.warning(SignUp.this,getString(R.string.password_chars),Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public void logIn(View v){
        Intent intent=new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
    }

    public void createUser(String email,String password,String name){
     auth.createUserWithEmailAndPassword(email,password)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful())
                     {
                         LoadingDialog loadingDialog=new LoadingDialog(SignUp.this);

                         FirebaseUser currentUser = auth.getCurrentUser();
                         updateUserData(currentUser,name);

                         User user=new User(currentUser.getUid(),currentUser.getEmail(),currentUser.getDisplayName());
                         AuthRole authRole=new AuthRole();
                        authRole.addUser(user);

                        UserHome userHome=new UserHome();
                        Context userHomeContext=userHome.getContext();
                        releaseHomePage(SignUp.this,userHomeContext);
                     } else {
                         Toasty.warning(SignUp.this, getString(R.string.signup_failed),
                                 Toast.LENGTH_SHORT).show();

                     }
                 }


             });


    }



    public static boolean isValidEmail(String email){
        if(email==null){
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    private void updateUserData(FirebaseUser user,String name){
        UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("update profile","success");
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EMAIL,emailEditText.getText().toString());
        outState.putString(NAME,nameEditText.getText().toString());
        outState.putString(PASSWORD,passwordEditText.getText().toString());
    }
}