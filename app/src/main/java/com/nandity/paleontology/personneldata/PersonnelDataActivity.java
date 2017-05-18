package com.nandity.paleontology.personneldata;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    private LinearLayoutManager mLinearLayoutManger;
    private String spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_data);
        ButterKnife.bind(this);
        initListener();
//        initView();
    }

    private void initListener() {
        //下拉框选择监听
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.personnel_date);
                spinnerType=languages[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        //下拉刷新
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView(spinnerType,etSearch.getText().toString());
                mRefresh.setRefreshing(false);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView(spinnerType,etSearch.getText().toString());
            }
        });
    }


    private void initView(String spinnerType,String searchText) {
        OkGo.post(PersonnelApi.DATE_Personnel)
                .params("type", spinnerType)
                .params("searchText",searchText)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        List<PersonnelBean> mPersonnelBeanList = GsonHelper.getDuanziBeanList(s);
                        mRvShowDate.setLayoutManager(mLinearLayoutManger);
                        mRvShowDate.setAdapter(new PersonnelAdapter(PersonnelDataActivity.this, mPersonnelBeanList));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
