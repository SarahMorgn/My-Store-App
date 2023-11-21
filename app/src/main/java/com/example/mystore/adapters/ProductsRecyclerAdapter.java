package com.example.mystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.example.mystore.classes.Product;
import com.example.mystore.fragments_of_tablayout_admin.ProductsFragment;
import com.example.mystore.pages.ProductData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder> {
    private static final String EDIT_PRODUCT = "EDIT_PRODUCT";
    private List<Product> productsList;
    private Context context;
    public ProductsRecyclerAdapter(List<Product> productsList, Context context){
        this.context=context;
        this.productsList=productsList;
    }
    public void setData(List<Product> productsList){
        this.productsList.clear();
        this.productsList.addAll(productsList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=productsList.get(position);
        Picasso.get().load(product.getImgUrl()).placeholder(R.drawable.baseline_image_120).into(holder.imageView);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getPrice()+"EGP");

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProductIntent=new Intent(context, ProductData.class);
                editProductIntent.putExtra(EDIT_PRODUCT,product);
                context.startActivity(editProductIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView productName,productPrice;
        Button editBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.productItemImg);
            productName=itemView.findViewById(R.id.productName);
            productPrice=itemView.findViewById(R.id.productItemPrice);
            editBtn=itemView.findViewById(R.id.edit_productItem);
        }
    }
}
