package com.nandity.paleontology.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * DialogUtils工具类
 */
public class DialogUtils {
    private ProgressDialog progressDialog;
    private Context context;

    public DialogUtils(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载...");
    }

    public void showDialog(){
        if(progressDialog==null){
        }else {
            progressDialog.show();
        }
    }

    public void deleteDialog(){
        if (progressDialog!=null) {
            progressDialog.cancel();
        }
    }
}
