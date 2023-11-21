package com.example.mystore.classes;

import android.app.Dialog;
import android.content.Context;

import com.example.mystore.R;
import com.example.mystore.SignUp;

public class LoadingDialog {
    Dialog loadingDialog;
    public LoadingDialog(Context context){
        loadingDialog=new Dialog(context);
        loadingDialog.setContentView(R.layout.loading_view);
        loadingDialog.show();
    }
    public void dismiss(){
        loadingDialog.dismiss();
    }
}
