package com.nandity.paleontology.common.fragment_main;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.common.UpdataService;
import com.nandity.paleontology.login.LoginActivity;
import com.nandity.paleontology.personneldata.PersonnelDataActivity;
import com.nandity.paleontology.relicdata.ui.PaleontologicalActivity;
import com.nandity.paleontology.util.ActivityCollectorUtils;
import com.nandity.paleontology.util.AppUtils;
import com.nandity.paleontology.util.LogUtils;
import com.nandity.paleontology.util.SharedUtils;
import com.nandity.paleontology.util.ToActivityUtlis;
import com.nandity.paleontology.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by qingsong on 2017/5/27.
 */

public class Fragment_work extends Fragment {


    @BindView(R.id.ll_personal_data)
    LinearLayout llPersonalData;
    @BindView(R.id.ll_paleontological_data)
    LinearLayout llPaleontologicalData;
    @BindView(R.id.ll_vido_data)
    LinearLayout llVidoData;
    private String TAG = "Qingsong", msg, status, sessionId;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        return view;
    }


    //有别的设备登录，返回登录页面
    private void initToLogin(String msg) {
        SharedUtils.putShare(mContext, "isLogin", false);
        ToastUtils.showLong(mContext, msg);
        ToActivityUtlis.toNextActivity(mContext, LoginActivity.class);
        ActivityCollectorUtils.finishAll();
    }

    private void updateManager() {
        LogUtils.i(TAG, "进入更新");
        OkGo.post(new Api(mContext).getUpdateVerCodeUrl())
                .params("versionNumber", AppUtils.getVerCode(mContext))
                .params("sessionId", sessionId)
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
                            } else if ("400".equals(status)) {
                                initToLogin(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void showNoticeDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(msg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent service = new Intent(mContext, UpdataService.class);
                mContext.startService(service);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ll_personal_data, R.id.ll_paleontological_data, R.id.ll_vido_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_personal_data:
                ToActivityUtlis.toNextActivity(mContext, PersonnelDataActivity.class);
                break;
            case R.id.ll_paleontological_data:
                ToActivityUtlis.toNextActivity(mContext, PaleontologicalActivity.class);
                break;
            case R.id.ll_vido_data:
                break;
        }
    }
}
