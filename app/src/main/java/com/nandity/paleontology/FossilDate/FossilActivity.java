package com.nandity.paleontology.FossilDate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.push.CloudPushService;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.common.BaseActivity;
import com.nandity.paleontology.login.LoginActivity;
import com.nandity.paleontology.relicdata.ui.ReLicDataActivity;
import com.nandity.paleontology.util.ActivityCollectorUtils;
import com.nandity.paleontology.util.SharedUtils;
import com.nandity.paleontology.util.ToActivityUtlis;
import com.nandity.paleontology.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class FossilActivity extends BaseActivity {

    @BindView(R.id.packingNum)
    TextView packingNum;
    @BindView(R.id.fossiNum)
    TextView fossiNum;
    @BindView(R.id.fossilSpecies)
    TextView fossilSpecies;
    @BindView(R.id.fossilSystemClassification_1)
    TextView fossilSystemClassification1;
    @BindView(R.id.fossilSystemClassification_2)
    TextView fossilSystemClassification2;
    @BindView(R.id.fossilSystemClassification_3)
    TextView fossilSystemClassification3;
    @BindView(R.id.fossilSystemClassification_4)
    TextView fossilSystemClassification4;
    @BindView(R.id.earlyFossilName)
    TextView earlyFossilName;
    @BindView(R.id.excavationUnit)
    TextView excavationUnit;
    @BindView(R.id.excavationPerson)
    TextView excavationPerson;
    @BindView(R.id.excavationDate)
    TextView excavationDate;
    @BindView(R.id.producer)
    TextView producer;
    @BindView(R.id.possilLon)
    TextView possilLon;
    @BindView(R.id.possilLat)
    TextView possilLat;
    @BindView(R.id.geologicaAge)
    TextView geologicaAge;
    @BindView(R.id.stratumHorizon)
    TextView stratumHorizon;
    @BindView(R.id.lithology)
    TextView lithology;
    @BindView(R.id.burialState)
    TextView burialState;
    @BindView(R.id.keepstatus)
    TextView keepstatus;
    @BindView(R.id.featureDescription)
    TextView featureDescription;
    @BindView(R.id.fossilWhereabouts)
    TextView fossilWhereabouts;
    @BindView(R.id.fossilSize_1)
    TextView fossilSize1;
    @BindView(R.id.fossilSize_2)
    TextView fossilSize2;
    @BindView(R.id.fossilSize_3)
    TextView fossilSize3;
    @BindView(R.id.goBackFossilData)
    ImageView goBackFossilData;
    @BindView(R.id.fossiPhoto)
    ImageView fossiPhoto;
    private FossiBean fossiBean;
    private String sessionId;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fossil);
        ButterKnife.bind(this);
        mContext=this;
        sessionId = (String) SharedUtils.getShare(this, "sessionId", "");
//        fossiBean= FossilDateActivity.list.get(0);
        ArrayList<FossiBean> list = (ArrayList<FossiBean>) getIntent().getSerializableExtra("list");
        fossiBean = list.get(0);
        initdate();
        initListener();
    }

    private void initListener() {
        goBackFossilData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initdate() {

        fossiNum.setText(fossiBean.getFossil_no());//化石编号
        packingNum.setText(fossiBean.getEncasement_no());//装箱编号

        //fossilSpecies.setText(fossiBean.getFossil_type());//化石种类
        setOkhttpNum(fossiBean.getFossil_type(),fossilSpecies);

        fossilSystemClassification1.setText(fossiBean.getDoors());//门
        fossilSystemClassification2.setText(fossiBean.getClasses());//纲
        fossilSystemClassification3.setText(fossiBean.getOrders());//目
        fossilSystemClassification4.setText(fossiBean.getFamilies());//科
        earlyFossilName.setText(fossiBean.getInitial_fossil_name());//初定化石名称
        excavationUnit.setText(fossiBean.getExplore_unit());//发掘单位
        excavationPerson.setText(fossiBean.getExplore_man());//发掘人员
        excavationDate.setText(fossiBean.getExplore_time());//发掘时间
        producer.setText(fossiBean.getProducing_area());//产地
        possilLon.setText(fossiBean.getPoint_n());//经度
        possilLat.setText(fossiBean.getPoint_e());//经度

        //geologicaAge.setText(fossiBean.getDizhishidai());//地质时代
        setOkhttpNum(fossiBean.getDizhishidai(),geologicaAge);

        //stratumHorizon.setText(fossiBean.getDicengcengwei());//地层层位
        setOkhttpNum(fossiBean.getDicengcengwei(),stratumHorizon);

        //lithology.setText(fossiBean.getYanxing());//岩性
        setOkhttpNum(fossiBean.getYanxing(),lithology);

        burialState.setText(fossiBean.getMaicanghzuangtai());//埋藏状态

        //keepstatus.setText(fossiBean.getPreservation_status());//保存状况
        setOkhttpNum(fossiBean.getPreservation_status(),keepstatus);


        featureDescription.setText(fossiBean.getFeature_description());//特征描述

        //fossilWhereabouts.setText(fossiBean.getFossil_whereabouts());//化石去向
        setOkhttpNum(fossiBean.getFossil_whereabouts(),fossilWhereabouts);


        fossilSize1.setText(fossiBean.getLongness());
        fossilSize2.setText(fossiBean.getWide());
        fossilSize3.setText(fossiBean.getHeight());
        if (TextUtils.isEmpty(fossiBean.getPhoto())) {
            Glide.with(this)
                    .load(R.mipmap.ic_launcher)
                    .into(fossiPhoto);
        } else {
            Glide.with(this)
                    .load(new Api(this).getPictureDataUrl() + fossiBean.getPhoto())
                    .into(fossiPhoto);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void setOkhttpNum(String id, final TextView tv) {
        final String[] s1 = new String[1];
        OkGo.post(new Api(this).getfindStaticValueDataUrl())
                .params("sessionId", sessionId)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("limeng", "s" + s);
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            String message = js.optString("message");
                            String status = js.optString("status");
                            if (status.equals("200")) {
                                tv.setText(js.getJSONArray("message").getJSONObject(0).getString("text"));
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
                        ToastUtils.showShort(mContext, "网络请求失败");
                        finish();
                    }
                });
    }

    //有别的设备登录，返回登录页面
    private void initToLogin(String msg) {
        CloudPushService pushService;
        pushService= AlibabaSDK.getService(CloudPushService.class);
        pushService.unbindAccount();
        SharedUtils.putShare(mContext, "isLogin", "-1");
        ToastUtils.showLong(mContext, msg);
        ToActivityUtlis.toNextActivity(mContext, LoginActivity.class);
        ActivityCollectorUtils.finishAll();
    }
}
