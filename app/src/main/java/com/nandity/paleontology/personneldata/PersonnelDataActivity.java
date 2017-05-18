package com.nandity.paleontology.personneldata;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class PersonnelDataActivity extends AppCompatActivity {

    @BindView(R.id.date_show)
    RecyclerView mRvShowDate;
    @BindView(R.id.date_refresh)
    SwipeRefreshLayout mRefresh;
    private LinearLayoutManager mLinearLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_data);
        ButterKnife.bind(this);
        //initView();
        initRefresh();
    }
    private void initRefresh() {
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                mRefresh.setRefreshing(false);
            }
        });
    }

    private void initView() {
        OkGo.post(PersonnelApi.DATE_Personnel)
                .params("","")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        List<PersonnelBean> mPersonnelBeanList = GsonHelper.getDuanziBeanList(s);
                        mRvShowDate.setLayoutManager(mLinearLayoutManger);
                        mRvShowDate.setAdapter(new DuanziAdapter(PersonnelDataActivity.this, mPersonnelBeanList));
                }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
