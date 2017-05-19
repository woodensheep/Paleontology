package com.nandity.paleontology.relicdata.ui;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.personneldata.PersonnelApi;
import com.nandity.paleontology.relicdata.util.PaleGsonHelper;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;
import com.nandity.paleontology.util.ToActivityUtlis;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class PaleontologicalActivity extends AppCompatActivity {

    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.date_show)
    RecyclerView dateShow;
    @BindView(R.id.date_refresh)
    SwipeRefreshLayout dateRefresh;
    private LinearLayoutManager mLinearLayoutManger;
    private String spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paleontological);
        ButterKnife.bind(this);
        initListener();
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
        dateRefresh.setColorSchemeResources(R.color.colorPrimary);
        dateRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView(spinnerType,etSearch.getText().toString());
                dateRefresh.setRefreshing(false);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initView(spinnerType,etSearch.getText().toString());
                ToActivityUtlis.toNextActivity(PaleontologicalActivity.this,ReLicDataActivity.class);
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
                        List<PaleontologicalaBean> mPaleontoBeanList = PaleGsonHelper.mPaleontoBeanList(s);
                        dateShow.setLayoutManager(mLinearLayoutManger);
                        mPaleontoAdapter=new PaleontoAdapter(PaleontologicalActivity.this,mPaleontoBeanList);
                        mPaleontoAdapter.setOnItemClickListener(new PaleontoAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Log.d("limeng","-------position------"+position);
                            }
                        });
                        dateShow.setAdapter(mPaleontoAdapter);
                    }
                });
    }

    private void initceshi(){

        List<PaleontologicalaBean> mPaleontoBeanList =new ArrayList<>();
        dateShow.setLayoutManager(mLinearLayoutManger);
        mPaleontoAdapter=new PaleontoAdapter(PaleontologicalActivity.this,mPaleontoBeanList);
        mPaleontoAdapter.setOnItemClickListener(new PaleontoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("limeng","-------position------"+position);
            }
        });
        dateShow.setAdapter(mPaleontoAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
