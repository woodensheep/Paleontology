package com.nandity.paleontology.FossilDate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.common.BaseActivity;
import com.nandity.paleontology.login.LoginActivity;
import com.nandity.paleontology.util.ActivityCollectorUtils;
import com.nandity.paleontology.util.JsonFormat;
import com.nandity.paleontology.util.LogUtils;
import com.nandity.paleontology.util.SharedUtils;
import com.nandity.paleontology.util.ToActivityUtlis;
import com.nandity.paleontology.util.ToastUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class FossilDateActivity extends BaseActivity {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.date_show)
    PullLoadMoreRecyclerView dateShow;
    @BindView(R.id.ll_normal)
    LinearLayout llNormal;
    @BindView(R.id.goBackRelc)
    ImageView goBackRelc;
    @BindView(R.id.fossilDate_title)
    TextView fossilDateTitle;
    private String sessionId;
    private String FossiId;
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;
    private FossiAdapter fossiAdapter;
    private List<FossiBean> fossiBeanList;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fossil_date);
        ButterKnife.bind(this);
        mContext = this;
        FossiId = getIntent().getStringExtra("Relicadata_id");
        String title = getIntent().getStringExtra("Relicadata_name");
        fossilDateTitle.setText(title);
        sessionId = (String) SharedUtils.getShare(this, "sessionId", "");
        initView();
        initDialogAndRecy();

    }

    //初始化dialog和recycerview
    private void initDialogAndRecy() {
        goBackRelc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        mRecyclerView = dateShow.getRecyclerView();
        mRecyclerView.setVerticalScrollBarEnabled(true);
        dateShow.setFooterViewText("正在加载...");
        dateShow.setPullRefreshEnable(false);
        dateShow.setLinearLayout();

    }

    private void initceshi(final List<FossiBean> fossiBeanList) {
        LogUtils.i("TAG", "进入initceshi" + fossiBeanList.size());
        fossiAdapter = new FossiAdapter(FossilDateActivity.this, fossiBeanList);
        fossiAdapter.setOnItemClickListener(new FossiAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(FossilDateActivity.this, FossilActivity.class);
                intent.putExtra("list", (Serializable) fossiBeanList);
                startActivity(intent);
            }
        });
        dateShow.setAdapter(fossiAdapter);

    }

    private void initView() {
        LogUtils.i("TAG", "进入initview" + sessionId + "----" + FossiId);
        OkGo.post(new Api(this).getFossilUrl())
                .params("sessionId", sessionId)
                .params("id", FossiId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        progressDialog.dismiss();
                        LogUtils.i("TAG", s.toString());
                        Log.d("limeng", s);
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            String message = js.optString("message");
                            String status = js.optString("status");
                            if (status.equals("200")) {
                                fossiBeanList = JsonFormat.stringToList(message, FossiBean.class);
                                initceshi(fossiBeanList);
                                Log.d("qingsong", fossiBeanList.toString());
                            } else if (status.equals("400")) {
                                initToLogin(message);
                            } else {
                                ToastUtils.showShort(mContext, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressDialog.dismiss();
                    }
                });

    }

    //有别的设备登录，返回登录页面
    private void initToLogin(String msg) {
        SharedUtils.putShare(mContext, "isLogin", false);
        ToastUtils.showLong(mContext, msg);
        ToActivityUtlis.toNextActivity(mContext, LoginActivity.class);
        ActivityCollectorUtils.finishAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}