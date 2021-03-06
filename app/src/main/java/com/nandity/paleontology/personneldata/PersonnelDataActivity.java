package com.nandity.paleontology.personneldata;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.push.CloudPushService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.FossilDate.FossiAdapter;
import com.nandity.paleontology.FossilDate.FossilActivity;
import com.nandity.paleontology.FossilDate.FossilDateActivity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class PersonnelDataActivity extends BaseActivity {
    @BindView(R.id.goBackBtn)
    ImageView goBackBtn;
    private String TAG = "Qingsong";
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.ll_123)
    LinearLayout ll123;
    @BindView(R.id.date_show)
    PullLoadMoreRecyclerView dateShow;
    @BindView(R.id.ll_normal)
    LinearLayout llNormal;
    @BindView(R.id.progresbar)
    ProgressBar progresbar;
    @BindView(R.id.search_progress)
    RelativeLayout searchProgress;
    private List<PersonnelBean> personnelBeanlist = new ArrayList<>();
    private int pageNum = 0;
    private static int rowsNum = 10;
    private String sessionId = "";
    private RecyclerView mRecyclerView;
    private PersonnelAdapter normalAdapter;
    private Context mContext = PersonnelDataActivity.this;
    private String spinnerType = "0",number="0";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_data);
        ButterKnife.bind(this);
        sessionId = (String) SharedUtils.getShare(this, "sessionId", "");
        initView();
        initListener();
        initDialogAndRecy();
    }

    //初始化dialog和recycerview
    private void initDialogAndRecy() {
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        mRecyclerView = dateShow.getRecyclerView();
        mRecyclerView.setVerticalScrollBarEnabled(true);
        dateShow.setFooterViewText("正在加载...");
        dateShow.setPullRefreshEnable(false);
        dateShow.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                dateShow.setRefreshing(true);
                loadMore();
            }
        });

    }

       //设置adapter
    private void setAdapter() {
        dateShow.setLinearLayout();
        normalAdapter = new PersonnelAdapter(mContext, personnelBeanlist);
        PersonnelBean personnelBean= personnelBeanlist.get(0);
        number=personnelBean.getIphone();
        normalAdapter.setOnItemClickListener(new PersonnelAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                call(number);
            }
        });
        dateShow.setAdapter(normalAdapter);
    }


    private void call(String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel://" + number));
        startActivity(intent);

    }
    /**
     * 判断输入的是字符还是数字
     *
     * @param param
     * @return
     */
    private String getParamName(String param) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(param);
        if (m.matches()) {
            return "phone";
        }
        return "name";
    }

    //搜索加载
    private void searchView() {
        String param = etSearch.getText().toString();
        String paramName = getParamName(param);
        LogUtils.i(TAG, paramName + param + "type:" + spinnerType);
        progressDialog.show();
        OkGo.post(new Api(this).getPersonnelUrl())
                .params(paramName, param)
                .params("sessionId", sessionId)
                .params("type", spinnerType)
                .connTimeOut(10000)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        progressDialog.dismiss();
                        etSearch.setText("");
                        LogUtils.i("Qingsong", "搜索的数据：" + s.toString());
                        String msg, status;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            msg = jsonObject.optString("message");
                            status = jsonObject.optString("status");
                            if (status.equals("200")) {
                                personnelBeanlist = JsonFormat.stringToList(msg, PersonnelBean.class);
                                setAdapter();
                            } else if (status.equals("400")) {
                                initToLogin(msg);
                            } else if (status.equals("500")) {
                                ToastUtils.showLong(mContext, "搜索条件不匹配");
                                dateShow.setPullLoadMoreCompleted();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LogUtils.i(TAG, e.toString());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        progressDialog.dismiss();
                        ToastUtils.showLong(mContext, "网络请求失败");
                    }
                });

    }

    //监听
    private void initListener() {
        //下拉框选择监听
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.personnel_date);
                spinnerType = id + "";
                LogUtils.i(TAG, spinnerType + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //搜索按钮监听
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView();
            }
        });
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //上拉加载
    private void loadMore() {
        pageNum += rowsNum;
        OkGo.post(new Api(this).getPersonnelUrl())
                .params("page", pageNum + "")
                .params("rows", rowsNum + "")
                .connTimeOut(10000)
                .params("sessionId", sessionId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        progressDialog.dismiss();
                        LogUtils.i("Qingsong", "上拉加载的数据：" + s.toString());
                        String msg, status;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            msg = jsonObject.optString("message");
                            status = jsonObject.optString("status");
                            if (status.equals("200")) {
                                List<PersonnelBean> loadmoreList = JsonFormat.stringToList(msg, PersonnelBean.class);
                                personnelBeanlist.addAll(loadmoreList);
                                normalAdapter.notifyDataSetChanged();
                                dateShow.setPullLoadMoreCompleted();
                            } else if (status.equals("400")) {
                                initToLogin(msg);
                            } else if (status.equals("500")) {
                                ToastUtils.showLong(mContext, msg);
                                dateShow.setPullLoadMoreCompleted();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressDialog.dismiss();
                        dateShow.setPullLoadMoreCompleted();
                        ToastUtils.showLong(mContext, "网络不给力请稍后");

                    }
                });
    }

    //有别的设备登录，返回登录页面
    private void initToLogin(String msg) {
        CloudPushService pushService;
        pushService= AlibabaSDK.getService(CloudPushService.class);
        pushService.unbindAccount();
        SharedUtils.putShare(this, "isLogin", "-1");
        ToastUtils.showLong(PersonnelDataActivity.this, msg);
        ToActivityUtlis.toNextActivity(mContext, LoginActivity.class);
        ActivityCollectorUtils.finishAll();
    }

    //第一次进入获取数据
    private void initView() {

        LogUtils.i("Qingsong", new Api(this).getPersonnelUrl());
        OkGo.post(new Api(this).getPersonnelUrl())
                .params("page", pageNum + "")
                .params("rows", rowsNum + "")
                .params("sessionId", sessionId)
                .connTimeOut(5000)
                .readTimeOut(5000)
                .cacheTime(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        progressDialog.dismiss();
                        String msg, status;
                        LogUtils.i("Qingsong", "第一次返回的数据：" + s.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            msg = jsonObject.optString("message");
                            status = jsonObject.optString("status");
                            if (status.equals("200")) {
                                personnelBeanlist = JsonFormat.stringToList(msg, PersonnelBean.class);
                                setAdapter();
                            } else if (status.equals("400")) {
                                initToLogin(msg);
                            } else {
                                ToastUtils.showLong(mContext, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressDialog.dismiss();
                        ToastUtils.showLong(mContext, "网络请求失败");
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
