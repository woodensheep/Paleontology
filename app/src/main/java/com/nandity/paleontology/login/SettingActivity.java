package com.nandity.paleontology.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.nandity.paleontology.R;
import com.nandity.paleontology.util.SharedUtils;
import com.nandity.paleontology.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends Activity {


    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_setting)
    Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_setting)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etMobile.getText().toString())){
            ToastUtils.showShort(this,"请输入ip");
        }else if (TextUtils.isEmpty(etPassword.getText().toString())){
            ToastUtils.showShort(this,"请输入端口");
        }else{
            SharedUtils.putShare(this,"IP",etMobile.getText().toString());
            SharedUtils.putShare(this,"PORT",etPassword.getText().toString());
            finish();
        }

    }
}
