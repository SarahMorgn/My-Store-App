package com.example.mystore.classes;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {

    private String productName,description;
    private String imgUrl;
    private String price;
    private ArrayList<String> size;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product(){

    }
    public Product(String productName,String description,ArrayList<String> size,String price/*,String imgUri*/){
        this.price=price;
        this.productName=productName;
        this.description=description;
        this.size=size;
       // this.imgUri=imgUri;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUri(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getSize() {
        return size;
    }

    public void setSize(ArrayList<String> size) {
        this.size = size;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
