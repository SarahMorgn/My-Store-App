package com.example.mystore.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mystore.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AuthRole {
    private static final String AUTH_ROLE_TAG = "AuthRole";
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private CollectionReference collectionReference= db.collection("authors");

    public void addUser(User user){
        String email= user.getEmail();
        String id= user.getUserId();
        Map<String, String> userRecord=new HashMap<>();
        userRecord.put("email",email);
        userRecord.put("userId",id);
        userRecord.put("role","user");
        collectionReference.add(userRecord).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i(AUTH_ROLE_TAG,"Added Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(AUTH_ROLE_TAG,"Failed:"+e.getMessage().toString());
            }
        });
    }

}
