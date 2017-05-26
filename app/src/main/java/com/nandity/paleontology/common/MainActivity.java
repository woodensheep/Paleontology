package com.nandity.paleontology.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.FossilDate.FossilActivity;
import com.nandity.paleontology.R;
import com.nandity.paleontology.personneldata.PersonnelDataActivity;
import com.nandity.paleontology.relicdata.ui.PaleontologicalActivity;
import com.nandity.paleontology.util.AppUtils;
import com.nandity.paleontology.util.LogUtils;
import com.nandity.paleontology.util.ToActivityUtlis;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.ll_personal_data)
    LinearLayout llPersonalData;
    @BindView(R.id.action_update)
    FloatingActionButton actionUpdate;
    @BindView(R.id.action_signout)
    FloatingActionButton actionSignout;
    @BindView(R.id.actions_menu)
    FloatingActionsMenu actionsMenu;
    @BindView(R.id.ll_paleontological_data)
    LinearLayout llPaleontologicalData;
    @BindView(R.id.ll_vido_data)
    LinearLayout llVidoData;
    private boolean fabOpened = false;
    private String TAG = "Qingsong", msg, status;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListener();
        mContext = this;


//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!fabOpened){
//                    openMenu(view);
//                }else{
//                    closeMenu(view);
//                }
//            }
//        });
    }

    private void setListener() {
        llPersonalData.setOnClickListener(this);
        llPaleontologicalData.setOnClickListener(this);
        llVidoData.setOnClickListener(this);
        actionUpdate.setOnClickListener(this);
    }


    private void openMenu(View view) {
        fabOpened = true;
    }


    private void closeMenu(View view) {
        fabOpened = false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_update:
                updateManager();
                break;
            case R.id.action_signout:

                break;
            case R.id.ll_personal_data:
                ToActivityUtlis.toNextActivity(MainActivity.this, PersonnelDataActivity.class);
                break;
            case R.id.ll_paleontological_data:
                ToActivityUtlis.toNextActivity(MainActivity.this, PaleontologicalActivity.class);
                break;
            case R.id.ll_vido_data:
                ToActivityUtlis.toNextActivity(MainActivity.this, FossilActivity.class);

        }
    }

    private void updateManager() {
        LogUtils.i(TAG,"进入更新");
        OkGo.post(new Api(mContext).getUpdateVerCodeUrl())
                .params("versionNumber", AppUtils.getVerCode(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            status = object.getString("status");
                            msg = object.optString("message");
                            LogUtils.i(TAG, status);
                            if ("200".equals(status)) {
                                showNoticeDialog();
                            } else if ("300".equals(status)) {
//                                ToastUtils.showShortToast("暂时没有更新");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void showNoticeDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("软件版本更新");
        builder.setMessage(msg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent service = new Intent(MainActivity.this, UpdataService.class);
                startService(service);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}
