package com.nandity.paleontology.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.MainActivity;
import com.nandity.paleontology.util.ScreenZoomUtil;
import com.nandity.paleontology.util.ToActivityUtlis;
import com.nandity.paleontology.util.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends FragmentActivity implements View.OnClickListener {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.forget_password)
    TextView forget_password;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.root)
    RelativeLayout root;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private int height = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidBug5497Workaround.assistActivity(this);
        ButterKnife.bind(this);
        intiView();
        initListener();

    }

    private void intiView() {
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 4;//弹起高度为屏幕高度的1/3
    }

    private void initListener() {
        ivCleanPhone.setOnClickListener(this);
        cleanPassword.setOnClickListener(this);
        ivShowPwd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && ivCleanPhone.getVisibility() == View.GONE) {
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    ivCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && cleanPassword.getVisibility() == View.GONE) {
                    cleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    cleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    etPassword.setSelection(s.length());
                }
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        findViewById(R.id.root).addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);
                    ScreenZoomUtil.zoomIn(logo, (oldBottom - bottom) - keyHeight);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);
                    ScreenZoomUtil.zoomOut(logo, (bottom - oldBottom) - keyHeight);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String pwd = etPassword.getText().toString();
        String mobile = etMobile.getText().toString();
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                etMobile.setText("");
                break;
            case R.id.clean_password:
                etPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (etPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.ic_visibly);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.ic_visibly_up);
                }
                if (!TextUtils.isEmpty(pwd))
                    etPassword.setSelection(pwd.length());
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showShort(this, "请输入账号");
                } else if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShort(this, "请输入密码");
                } else {
//                    send();
                    ToActivityUtlis.toNextActivity(LoginActivity.this, MainActivity.class);
                    finish();
                }
                break;
            case R.id.forget_password:
                ToActivityUtlis.toNextActivity(LoginActivity.this, SettingActivity.class);
                break;
        }
    }

    private void send() {
        OkGo.get("https://www.baidu.com")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("s" + s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

}
