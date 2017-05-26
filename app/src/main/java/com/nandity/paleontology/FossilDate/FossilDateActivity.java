package com.nandity.paleontology.FossilDate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.personneldata.PersonnelAdapter;
import com.nandity.paleontology.relicdata.ui.PaleontoAdapter;
import com.nandity.paleontology.relicdata.ui.PaleontologicalActivity;
import com.nandity.paleontology.relicdata.ui.ReLicDataActivity;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;
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

public class FossilDateActivity extends AppCompatActivity {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.date_show)
    PullLoadMoreRecyclerView dateShow;
    @BindView(R.id.ll_normal)
    LinearLayout llNormal;
    private LinearLayoutManager mLinearLayoutManger;
    private String sessionId;
    private String FossiId;
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;
    private FossiAdapter fossiAdapter;
    private List<FossiBean> fossiBeanList;
    public  static  List<FossiBean>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fossil_date);
        ButterKnife.bind(this);
        FossiId = getIntent().getStringExtra("Relicadata_id");
        sessionId = (String) SharedUtils.getShare(this, "sessionId", "");
        initView();
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
        dateShow.setLinearLayout();

    }

    private void initceshi(final List<FossiBean> fossiBeanList) {
        LogUtils.i("TAG", "进入initceshi" +fossiBeanList.size());
        fossiAdapter = new FossiAdapter(FossilDateActivity.this, fossiBeanList);
        fossiAdapter.setOnItemClickListener(new FossiAdapter.OnItemClickListener() {
          @Override
          public void onClick(int position) {
              list=fossiBeanList; //传值到FossilActivity
             Intent intent= new Intent(FossilDateActivity.this,FossilActivity.class);
              intent.putExtra("list",(Serializable)fossiBeanList);
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
                                fossiBeanList= JsonFormat.stringToList(message,FossiBean.class);
                                initceshi(fossiBeanList);
                                Log.d("qingsong", fossiBeanList.toString());
                            } else if (status.equals("500")) {
                                ToastUtils.showShort(FossilDateActivity.this, message);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}