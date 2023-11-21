package com.example.mystore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mystore.R;
import com.example.mystore.auths.AdminHome;
import com.example.mystore.auths.UserHome;
import com.example.mystore.classes.LoadingDialog;
import com.example.mystore.classes.User;
import com.example.mystore.firestore.AuthRole;
import com.example.mystore.view_models.RoleViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LogIn extends AppCompatActivity {
    private static final String LOGIN_EMAIL = "LOGIN_EMAIL";
    private static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";
EditText emailEditText,passwordEditText;
Button logIn;
FirebaseAuth auth;
RoleViewModel roleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();

        emailEditText=findViewById(R.id.forgotPasswordByEmail);
        passwordEditText=findViewById(R.id.logInPassword);
        logIn=findViewById(R.id.reset);

        roleViewModel= new ViewModelProvider(this).get(RoleViewModel.class);

        if(savedInstanceState!=null){
            emailEditText.setText(savedInstanceState.getString(LOGIN_EMAIL));
            passwordEditText.setText(savedInstanceState.getString(LOGIN_PASSWORD));
        }
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SignUp.isValidEmail(emailEditText.getText().toString())){
                    logInUser(emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }else {
                    Toasty.warning(LogIn.this,getString(R.string.invalid_email),Toasty.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void logInUser(String email,String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("Log in","success");
                            FirebaseUser currentUser=auth.getCurrentUser();
                            User user=new User(currentUser.getUid(),currentUser.getEmail(),currentUser.getDisplayName());
                            roleViewModel.checkAuthRole(user,LogIn.this);
                        }
                        else {

                            Toasty.warning(LogIn.this,getString(R.string.wrong_EP),Toasty.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void forgotPassword(View v){
        Intent intent=new Intent(LogIn.this,ForgotPassword.class);
        startActivity(intent);
    }
    public void signUp(View v){
        Intent intent=new Intent(LogIn.this,SignUp.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LOGIN_EMAIL,emailEditText.getText().toString());
        outState.putString(LOGIN_PASSWORD,passwordEditText.getText().toString());
    }
    public static void releaseHomePage(Context context, Context homePage){
        LoadingDialog loadingDialog=new LoadingDialog(context);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            loadingDialog.dismiss();
            Intent intent=new Intent(context,homePage.getClass());
            context.startActivity(intent);
            }
        },3000);
    }
}