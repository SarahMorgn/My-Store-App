package com.example.mystore.view_models;

import static com.example.mystore.LogIn.releaseHomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mystore.LogIn;
import com.example.mystore.auths.AdminHome;
import com.example.mystore.auths.UserHome;
import com.example.mystore.classes.LoadingDialog;
import com.example.mystore.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class RoleViewModel extends ViewModel {
    private static final String AUTH_ROLE_TAG = "RoleViewModelTAG";
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("authors");

    public void checkAuthRole(User user, Context context){
        String id= user.getUserId();
        collectionReference.whereEqualTo("userId",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    String role= documentSnapshot.getString("role");
                    if(role.equals("admin")){
                        AdminHome adminHome=new AdminHome();
                        Context adminHomeContext= adminHome.getContext();
                        releaseHomePage(context,adminHomeContext);
                    }
                    else {
                        UserHome userHome=new UserHome();
                        Context userHomeContext= userHome.getContext();
                        releaseHomePage(context,userHomeContext);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(AUTH_ROLE_TAG,"get Role Failed:"+e.getMessage().toString());
            }
        });

    }


}
