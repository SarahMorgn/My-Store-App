package com.example.mystore.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mystore.classes.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductDataViewModel extends ViewModel {
       FirebaseFirestore db=FirebaseFirestore.getInstance();
       CollectionReference collectionReference= db.collection("Products");

       MutableLiveData<List<Product>> productsData=new MutableLiveData<>();

       public LiveData<List<Product>> getProductData(){
           collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   List<Product> productsList=new ArrayList<>();
                   for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                       DocumentReference documentReference=documentSnapshot.getReference();
                       Product product=documentSnapshot.toObject(Product.class);
                       product.setId(documentReference.getId());
                       productsList.add(product);
                   }
                   productsData.postValue(productsList);
               }
           });
           return productsData;
       }
}
