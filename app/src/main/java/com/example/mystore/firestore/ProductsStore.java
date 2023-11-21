package com.example.mystore.firestore;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mystore.R;
import com.example.mystore.classes.Product;
import com.example.mystore.pages.ProductData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ProductsStore {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference collectionReference=db.collection("Products");

    public void addProduct(Product product, Context context,String imgUrl){
        product.setImgUri(imgUrl);
        collectionReference.add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toasty.success(context, R.string.success,Toasty.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(context, R.string.failed,Toasty.LENGTH_SHORT).show();
            }
        });

    }

    public void updateProduct(Product product,Context context,String imgUrl){
        product.setImgUri(imgUrl);
        String documentId= product.getId();
        collectionReference.document(documentId).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toasty.success(context, R.string.success,Toasty.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(context, R.string.failed,Toasty.LENGTH_SHORT).show();
            }
        });
    }

}
