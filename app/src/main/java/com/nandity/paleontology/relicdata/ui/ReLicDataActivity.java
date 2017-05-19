package com.nandity.paleontology.relicdata.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.FossilDate.FossiAdapter;
import com.nandity.paleontology.FossilDate.FossilDateActivity;
import com.nandity.paleontology.R;
import com.nandity.paleontology.personneldata.PersonnelApi;
import com.nandity.paleontology.relicdata.util.PaleGsonHelper;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lemon on 2017/5/18.
 */

public class ReLicDataActivity extends FragmentActivity {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.date_show)
    RecyclerView dateShow;
    @BindView(R.id.date_refresh)
    SwipeRefreshLayout dateRefresh;
    private LinearLayoutManager mLinearLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relic_data);

        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {

        //下拉刷新
        dateRefresh.setColorSchemeResources(R.color.colorPrimary);
        dateRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                dateRefresh.setRefreshing(false);
            }
        });

    }


    private void initView() {
        OkGo.post(PersonnelApi.DATE_Personnel)
                .params("type", "")
                .params("searchText", "")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        List<PaleontologicalaBean> mPaleontoBeanList = PaleGsonHelper.mPaleontoBeanList(s);
                        dateShow.setLayoutManager(mLinearLayoutManger);
                        dateShow.setAdapter(new FossiAdapter(ReLicDataActivity.this, mPaleontoBeanList));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
