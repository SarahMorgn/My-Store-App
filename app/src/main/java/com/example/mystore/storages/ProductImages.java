package com.example.mystore.storages;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mystore.classes.Product;
import com.example.mystore.pages.ProductData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ProductImages {
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    public void addProductPhoto(Uri imgUri,OnImageCompleteListener listener)  {
        String fileName = UUID.randomUUID().toString();

        StorageReference imgReference = storageReference.child("productImages/" + fileName);
        imgReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("IMG ","Uploaded Successfully");
                imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                          listener.onComplete(uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("IMG",e.getMessage().toString());
            }
        });

    }

    public void updateProductPhoto(Uri newImgUri,String oldImgUrl,OnImageCompleteListener listener){
        deleteProductImg(oldImgUrl);
        addProductPhoto(newImgUri,listener);
    }

    private void deleteProductImg(String oldImgUrl) {
        StorageReference imgReference=FirebaseStorage.getInstance().getReferenceFromUrl(oldImgUrl);
        imgReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("Img Store","img deleted successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Img Store","img deleted failed");
            }
        });
    }
}
