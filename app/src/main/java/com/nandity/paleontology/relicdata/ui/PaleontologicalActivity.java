package com.nandity.paleontology.relicdata.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.common.BaseActivity;
import com.nandity.paleontology.login.LoginActivity;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;
import com.nandity.paleontology.util.ActivityCollectorUtils;
import com.nandity.paleontology.util.DialogUtils;
import com.nandity.paleontology.util.JsonFormat;
import com.nandity.paleontology.util.LogUtils;
import com.nandity.paleontology.util.SharedUtils;
import com.nandity.paleontology.util.ToActivityUtlis;
import com.nandity.paleontology.util.ToastUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class PaleontologicalActivity extends BaseActivity {

    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.date_show)
    PullLoadMoreRecyclerView dateShow;
    @BindView(R.id.tv_nextRelicData)
    ImageView tvNextRelicData;
    private RecyclerView mRecyclerView;
    private String spinnerType;
    private PaleontoAdapter mPaleontoAdapter;
    private List<PaleontologicalaBean> mPaleontoBeanList;
    private String searchText;
    private String sessionId;
    private int pageNum = 0;
    private int rowsNum = 10;
    private Context context;
    private DialogUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paleontological);
        context = this;
        dialogUtils = new DialogUtils(context);
        ButterKnife.bind(this);
        initListener();
        sessionId = (String) SharedUtils.getShare(this, "sessionId", "");
        //initceshi();
        mPaleontoBeanList.clear();
        getSearchData();
        initView();
    }

    private void initListener() {
        tvNextRelicData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下拉框选择监听
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                //String[] languages = getResources().getStringArray(R.array.paleontological_data);
                if (pos == 0) {
                    spinnerType = "-1";
                } else {
                    spinnerType = (pos + 1) + "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initView(spinnerType,etSearch.getText().toString());
                //ToActivityUtlis.toNextActivity(PaleontologicalActivity.this,ReLicDataActivity.class);
                mPaleontoBeanList.clear();
                getSearchData();
                initView();
            }
        });


        mPaleontoBeanList = new ArrayList<>();
        dateShow.setLinearLayout();
        mPaleontoAdapter = new PaleontoAdapter(PaleontologicalActivity.this, mPaleontoBeanList);
        mPaleontoAdapter.setOnItemClickListener(new PaleontoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(PaleontologicalActivity.this, ReLicDataActivity.class);
                i.putExtra("palaeobios_name", mPaleontoBeanList.get(position).getPalaeobiosName());
                i.putExtra("palaeobios_id", mPaleontoBeanList.get(position).getId());
                startActivity(i);
                Log.d("limeng", "-------position------" + position);
            }
        });
        dateShow.setAdapter(mPaleontoAdapter);
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

    //上拉加载
    private void loadMore() {
        pageNum += rowsNum;
        OkGo.post(new Api(this).getPaleontogicalUrl())
                .params("palaeobiosType", spinnerType)
                .params("palaeobiosName", searchText)
                .params("sessionId", sessionId)
                .params("page", pageNum)
                .params("rows", rowsNum)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.i("limeng", "上拉加载的数据：" + s.toString());
                        String msg, status;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            msg = jsonObject.optString("message");
                            status = jsonObject.optString("status");
                            if (status.equals("200")) {
                                List<PaleontologicalaBean> loadmoreList = JsonFormat.stringToList(msg, PaleontologicalaBean.class);
                                mPaleontoBeanList.addAll(loadmoreList);
                                mPaleontoAdapter.notifyDataSetChanged();
                                dateShow.setPullLoadMoreCompleted();
                            } else if (status.equals("400")) {
                                initToLogin(msg);
                            } else if (status.equals("500")) {
                                ToastUtils.showLong(context, msg);
                                dateShow.setPullLoadMoreCompleted();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShort(context, "网络请求失败");
                    }
                });
    }

    private void getSearchData() {
        if (spinnerType == null) {
            spinnerType = "-1";
        }
        if (etSearch.getText().toString().trim().equals("")) {
            searchText = "";
        } else {
            searchText = etSearch.getText().toString().trim();
        }
    }
    //有别的设备登录，返回登录页面
    private void initToLogin(String msg) {
        SharedUtils.putShare(context, "isLogin", false);
        ToastUtils.showLong(context, msg);
        ToActivityUtlis.toNextActivity(context, LoginActivity.class);
        ActivityCollectorUtils.finishAll();
    }
    private void initView() {
        pageNum=0;
        dialogUtils.showDialog();
        OkGo.post(new Api(this).getPaleontogicalUrl())
                .params("palaeobiosType", spinnerType)
                .params("palaeobiosName", searchText)
                .params("sessionId", sessionId)
                .params("page", pageNum)
                .params("rows", rowsNum)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dialogUtils.deleteDialog();
                        Log.d("limeng", s);
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            String message = js.optString("message");
                            String status = js.optString("status");
                            if (status.equals("200")) {
                                List<PaleontologicalaBean> paleontoBeanList = JsonFormat.stringToList(message, PaleontologicalaBean.class);
                                Log.d("limeng", paleontoBeanList.toString());
                                initceshi(paleontoBeanList);
                            }else if (status.equals("400")) {
                                initToLogin(message);
                            } else if (status.equals("500")) {
                                ToastUtils.showShort(PaleontologicalActivity.this, message);
                                initceshi(new ArrayList<PaleontologicalaBean>());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dialogUtils.deleteDialog();
                        super.onError(call, response, e);
                        ToastUtils.showShort(context, "网络请求失败");
                    }
                });
    }

    private void initceshi(List<PaleontologicalaBean> paleontoBeanList) {
        mPaleontoBeanList = paleontoBeanList;
        mPaleontoAdapter = new PaleontoAdapter(PaleontologicalActivity.this, mPaleontoBeanList);
        mPaleontoAdapter.setOnItemClickListener(new PaleontoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(PaleontologicalActivity.this, ReLicDataActivity.class);
                i.putExtra("palaeobios_name", mPaleontoBeanList.get(position).getPalaeobiosName());
                i.putExtra("palaeobios_id", mPaleontoBeanList.get(position).getId());
                startActivity(i);
                Log.d("limeng", "-------position------" + position);
            }
        });
        dateShow.setAdapter(mPaleontoAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
