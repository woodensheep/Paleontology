package com.nandity.paleontology.relicdata.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import com.nandity.paleontology.relicdata.util.PaleGsonHelper;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;
import com.nandity.paleontology.util.JsonFormat;
import com.nandity.paleontology.util.SharedUtils;
import com.nandity.paleontology.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lemon on 2017/5/18.
 */

public class ReLicDataActivity extends FragmentActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_item_1)
    ImageView ivItem1;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_base_data1)
    TextView tvBaseData1;
    @BindView(R.id.tv_base_data2)
    TextView tvBaseData2;
    @BindView(R.id.tv_base_data3)
    TextView tvBaseData3;
    @BindView(R.id.tv_base_data4)
    TextView tvBaseData4;
    @BindView(R.id.tv_base_data5)
    TextView tvBaseData5;
    @BindView(R.id.tv_base_data6)
    TextView tvBaseData6;
    @BindView(R.id.tv_base_data7)
    TextView tvBaseData7;
    @BindView(R.id.tv_base_data8)
    TextView tvBaseData8;
    @BindView(R.id.tv_base_data9)
    TextView tvBaseData9;
    @BindView(R.id.tv_base_data10)
    TextView tvBaseData10;
    @BindView(R.id.tv_base_data11)
    TextView tvBaseData11;
    @BindView(R.id.tv_base_data12)
    TextView tvBaseData12;
    @BindView(R.id.tv_base_data13)
    TextView tvBaseData13;
    @BindView(R.id.tv_base_data14)
    TextView tvBaseData14;
    @BindView(R.id.tv_base_data15)
    TextView tvBaseData15;
    @BindView(R.id.tv_base_data16)
    TextView tvBaseData16;
    @BindView(R.id.tv_base_data17)
    TextView tvBaseData17;
    @BindView(R.id.tv_base_data18)
    TextView tvBaseData18;
    @BindView(R.id.tv_base_data19)
    TextView tvBaseData19;
    @BindView(R.id.tv_base_data20)
    TextView tvBaseData20;
    @BindView(R.id.ll_item_1)
    LinearLayout llItem1;
    @BindView(R.id.iv_item_2)
    ImageView ivItem2;
    @BindView(R.id.tv_scientific_1)
    TextView tvScientific1;
    @BindView(R.id.tv_scientific_2)
    TextView tvScientific2;
    @BindView(R.id.tv_scientific_3)
    TextView tvScientific3;
    @BindView(R.id.tv_scientific_4)
    TextView tvScientific4;
    @BindView(R.id.tv_scientific_5)
    TextView tvScientific5;
    @BindView(R.id.tv_scientific_6)
    TextView tvScientific6;
    @BindView(R.id.ll_item_2)
    LinearLayout llItem2;
    @BindView(R.id.iv_item_3)
    ImageView ivItem3;
    @BindView(R.id.tv_art_1)
    TextView tvArt1;
    @BindView(R.id.tv_art_2)
    TextView tvArt2;
    @BindView(R.id.tv_art_3)
    TextView tvArt3;
    @BindView(R.id.tv_art_4)
    TextView tvArt4;
    @BindView(R.id.tv_art_5)
    TextView tvArt5;
    @BindView(R.id.tv_art_6)
    TextView tvArt6;
    @BindView(R.id.ll_item_3)
    LinearLayout llItem3;
    @BindView(R.id.iv_item_4)
    ImageView ivItem4;
    @BindView(R.id.tv_nature_1)
    TextView tvNature1;
    @BindView(R.id.tv_nature_2)
    TextView tvNature2;
    @BindView(R.id.tv_nature_3)
    TextView tvNature3;
    @BindView(R.id.tv_nature_4)
    TextView tvNature4;
    @BindView(R.id.tv_nature_5)
    TextView tvNature5;
    @BindView(R.id.ll_item_4)
    LinearLayout llItem4;
    @BindView(R.id.iv_item_5)
    ImageView ivItem5;
    @BindView(R.id.tv_data_sources_1)
    TextView tvDataSources1;
    @BindView(R.id.tv_data_sources_2)
    TextView tvDataSources2;
    @BindView(R.id.tv_data_sources_3)
    TextView tvDataSources3;
    @BindView(R.id.ll_item_5)
    LinearLayout llItem5;
    @BindView(R.id.iv_item_6)
    ImageView ivItem6;
    @BindView(R.id.tv_picture_1)
    TextView tvPicture1;
    @BindView(R.id.ll_item_6)
    LinearLayout llItem6;
    @BindView(R.id.include_item_1)
    LinearLayout includeItem1;
    @BindView(R.id.include_item_2)
    LinearLayout includeItem2;
    @BindView(R.id.include_item_3)
    LinearLayout includeItem3;
    @BindView(R.id.include_item_4)
    LinearLayout includeItem4;
    @BindView(R.id.include_item_5)
    LinearLayout includeItem5;
    @BindView(R.id.include_item_6)
    LinearLayout includeItem6;
    private LinearLayoutManager mLinearLayoutManger;
    private Intent intent;
    private String title;
    private String id;
    private String sessionId;
    List<PaleontologicalaBean> paleontoBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relic_data);
        ButterKnife.bind(this);
        intent = getIntent();
        title = intent.getStringExtra("palaeobios_name");
        id = intent.getStringExtra("palaeobios_id");
        sessionId = (String) SharedUtils.getShare(this, "sessionId", "");
        initView();
        initListener();
        setOkGo();
    }

    private void initView() {
        tvTitle.setText(title);
    }

    private void initListener() {
        llItem1.setOnClickListener(this);
        llItem2.setOnClickListener(this);
        llItem3.setOnClickListener(this);
        llItem4.setOnClickListener(this);
        llItem5.setOnClickListener(this);
        llItem6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item_1:
                setVisibility(includeItem1);
                break;
            case R.id.ll_item_2:
                setVisibility(includeItem2);
                break;
            case R.id.ll_item_3:
                setVisibility(includeItem3);
                break;
            case R.id.ll_item_4:
                setVisibility(includeItem4);
                break;
            case R.id.ll_item_5:
                setVisibility(includeItem5);
                break;
            case R.id.ll_item_6:
                setVisibility(includeItem6);
                break;
        }
    }


    private void setVisibility(View view){
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);}
        else {
            view.setVisibility(View.GONE);
        }
    }

    private void setOkGo() {

        OkGo.post(new Api(this).getPalaeInfoUrl())
                .params("sessionId", sessionId)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("limeng", s);
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            String message = js.optString("message");
                            String status = js.optString("status");
                            if (status.equals("200")) {
                                paleontoBeanList = JsonFormat.stringToList(message,PaleontologicalaBean.class);
                                Log.d("limeng","____"+paleontoBeanList.toString());
                                setViewData();
                            } else {
                                ToastUtils.showShort(ReLicDataActivity.this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShort(ReLicDataActivity.this, "网络请求失败");
                    }
                });
    }

    private void setViewData() {
        PaleontologicalaBean p = paleontoBeanList.get(0);
        tvBaseData1.setText("");
        tvBaseData2.setText(p.getRelic_name());
        tvBaseData3.setText(p.getRelic_name());
        tvBaseData4.setText(p.getArea());
        tvBaseData5.setText(p.getUnit_id());
        tvBaseData6.setText(p.getKaifaxianzhuang());
        tvBaseData7.setText(p.getBaohuxianzhuang());
        tvBaseData8.setText(p.getDistrict());
        tvBaseData9.setText(p.getKaifajianyi());
        tvBaseData10.setText(p.getBaohujianyi());
        tvBaseData11.setText(p.getJiaotongzhuangkuang());
        tvBaseData12.setText(p.getArea());
        tvBaseData13.setText(p.getRelic_name());
        tvBaseData14.setText(p.getRelic_name());
        tvBaseData15.setText(p.getRelic_name());
        tvBaseData16.setText(p.getRelic_name());
        tvBaseData17.setText(p.getRelic_name());
        tvBaseData18.setText(p.getRelic_name());
        tvBaseData19.setText(p.getRelic_name());
        tvBaseData20.setText(p.getRelic_name());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
