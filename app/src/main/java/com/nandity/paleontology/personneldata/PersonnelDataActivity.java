package com.nandity.paleontology.personneldata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.login.LoginActivity;
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

import static android.R.id.list;

public class PersonnelDataActivity extends AppCompatActivity {
    private String TAG = "Qingsong";
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
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
    @BindView(R.id.search_recyclerview)
    RecyclerView searchRecyclerview;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.progresbar)
    ProgressBar progresbar;
    @BindView(R.id.search_progress)
    RelativeLayout searchProgress;
    private LinearLayoutManager mLinearLayoutManger;
    private PersonnelAdapter mPersonnelAdapter;
    private List<PersonnelBean> personnelBeanlist = new ArrayList<>();
    private int pageNum = 0;
    private static int rowsNum = 10;
    private String sessionId = "";
    private RecyclerView mRecyclerView;
    private PersonnelAdapter  normalAdapter;
    private  Context mContext;
    private  String  spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_data);
        ButterKnife.bind(this);

        sessionId = (String) SharedUtils.getShare(this, "sessionId", "");
        initView();
        initListener();
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
//                loadMore();
            }
        });


    }

    private void setAdapter() {
        dateShow.setLinearLayout();
        normalAdapter = new PersonnelAdapter(mContext, personnelBeanlist);
        dateShow.setAdapter(normalAdapter);
    }

    private void initListener() {
        //下拉框选择监听
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.personnel_date);
                spinnerType = languages[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });

    }

    private void loadMore() {
        pageNum += 10;
        OkGo.post(new Api(this).getPersonnelUrl())
                .params("page", pageNum + "")
                .params("rows", rowsNum + "")
                .params("sessionId", sessionId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.i("Qingsong", "上拉加载的数据：" + s.toString());
                        String msg,status;
                        try {
                            JSONObject jsonObject= new JSONObject(s);
                            msg=jsonObject.optString("message");
                            status=jsonObject.optString("status");
                            if (status.equals("200")){
                                personnelBeanlist= JsonFormat.stringToList(msg,PersonnelBean.class);
                                setAdapter();
                                itemClickListener(normalAdapter, personnelBeanlist, mRecyclerView);
                            }else if (status.equals("400")){
                                initToLogin(msg);
                            }else {
                                ToastUtils.showLong(mContext,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initToLogin(String msg) {
        ToastUtils.showLong(PersonnelDataActivity.this,msg);
        ToActivityUtlis.toNextActivity(mContext,LoginActivity.class);
        finish();
    }

    private void initView() {
        LogUtils.i("Qingsong", new Api(this).getPersonnelUrl());
        OkGo.post(new Api(this).getPersonnelUrl())
                .params("page", pageNum + "")
                .params("rows", rowsNum + "")
                .params("sessionId", sessionId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.i("Qingsong", "第一次返回的数据：" + s.toString());
                        String msg,status;
                        try {
                            JSONObject jsonObject= new JSONObject(s);
                            msg=jsonObject.optString("message");
                            status=jsonObject.optString("status");
                            if (status.equals("200")){
                                personnelBeanlist= JsonFormat.stringToList(msg,PersonnelBean.class);
                                setAdapter();
                                itemClickListener(normalAdapter, personnelBeanlist, mRecyclerView);
                            }else if (status.equals("400")){
                                initToLogin(msg);
                            }else {
                                ToastUtils.showLong(mContext,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }
                    });
                }

    private void itemClickListener(PersonnelAdapter adapter, final List<PersonnelBean> personnelBeanList, final RecyclerView recyclerView) {
        adapter.setOnItemClickListener(new PersonnelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                PersonnelBean bean = personnelBeanList.get(position);
            }
        });
    }
        @Override
        protected void onDestroy () {
            super.onDestroy();
        }
    }
