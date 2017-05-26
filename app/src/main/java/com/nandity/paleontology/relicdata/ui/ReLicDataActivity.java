package com.nandity.paleontology.relicdata.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nandity.paleontology.FossilDate.FossilDateActivity;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.common.BaseActivity;
import com.nandity.paleontology.login.LoginActivity;
import com.nandity.paleontology.relicdata.util.CustomImageView;
import com.nandity.paleontology.relicdata.util.ImageBean;
import com.nandity.paleontology.relicdata.util.NineGridlayout;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;
import com.nandity.paleontology.relicdata.util.ScreenTools;
import com.nandity.paleontology.util.ActivityCollectorUtils;
import com.nandity.paleontology.util.JsonFormat;
import com.nandity.paleontology.util.SharedUtils;
import com.nandity.paleontology.util.ToActivityUtlis;
import com.nandity.paleontology.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lemon on 2017/5/18.
 */

public class ReLicDataActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.tv_base_data21)
    TextView tvBaseData21;
    @BindView(R.id.tv_data_sources_4)
    TextView tvDataSources4;
    @BindView(R.id.iv_ngrid_layout)
    NineGridlayout ivNgridLayout;
    @BindView(R.id.iv_oneimage)
    CustomImageView ivOneimage;
    @BindView(R.id.tv_nextFossi)
    TextView tvNextFossi;
    @BindView(R.id.goBackPalo)
    ImageView goBackPalo;
    private LinearLayoutManager mLinearLayoutManger;
    private Intent intent;
    private String title;
    private String id;
    private String sessionId;
    List<PaleontologicalaBean> paleontoBeanList;
    private List<ImageBean> imagesList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relic_data);
        ButterKnife.bind(this);
        context = this;
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
        tvNextFossi.setOnClickListener(this);
        goBackPalo.setOnClickListener(this);

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
            case R.id.tv_nextFossi:
                Intent i = new Intent(ReLicDataActivity.this, FossilDateActivity.class);
                i.putExtra("Relicadata_id", id);
                i.putExtra("Relicadata_name",title);
                startActivity(i);
                break;
            case  R.id.goBackPalo:
                finish();
            default:

        }
    }


    private void setVisibility(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else {
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
                                paleontoBeanList = JsonFormat.stringToList(message, PaleontologicalaBean.class);
                                Log.d("limeng", "____" + paleontoBeanList.toString());
                                setViewData();
                            } else if (status.equals("400")){
                                initToLogin(message);
                            }else{
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

    //遗迹类型： 2：化石面结构 3:古无脊椎动物 4：古脊椎动物 5：古植物 6:足印 7：虫迹8：古人类化石9：旧石器文化遗址10：新石器文化遗址
    private static final String[] relicType = {"0", "1", "化石面结构", "古无脊椎动物", "古脊椎动物 ", "古植物", "足印", "虫迹", "古人类化石", "新石器文化遗址", "旧石器文化遗址"};

    //    自然地理条件：
//    旅游季节性：
//    环境质量：
//    脆弱性：
//    安全性：
    private static final String[] mNature1 = {"", "优美、决定作用(100-90)", "较好、重要作用(90-80)", "和谐、重要因素(80-70)", "融合、一定作用(70-50)", "整治保护因素( <50)"};
    private static final String[] mNature2 = {"", "极大(100-85)", "大(85-70)", "较大(70-60)", "较小(60-45)", "很小( <45)"};
    private static final String[] mNature3 = {"", "无(100-80)", "小(80-60)", "一般(60-40)", "较强(40-20)", "很强( <20)"};
    private static final String[] mNature4 = {"", "很好(100-80)", "好(80-60)", "较好(60-40)", "有不安全因素(40-20)", "较多不安全因素( <20 )"};
    private static final String[] mNature5 = {"", "无污染(100-85)", "很小(85-70)", "较小(70-60)", "污染(60-45)", "极重( <45)"};

    //    形象美:
//    色彩美:
//    动态美:
//    愉悦美:
//    奇特性和奇异性:
//    资源规模和组合:
    private static final String[] mArt1 = {"", "优美独特(100-85)", "优美(85-75)", "较好(75-60)", "一般(60-45)", "非艺术性( <45)"};
    private static final String[] mArt2 = {"", "优美独特(100-85)", "优美(85-75)", "较好(75-60)", "一般(60-45)", "非艺术性( <45)"};
    private static final String[] mArt3 = {"", "优美独特(100-85)", "优美(85-75)", "较好(75-60)", "一般(60-45)", "非艺术性( <45)"};
    private static final String[] mArt4 = {"", "极高(100-85)", "很高(85-70)", "较高(70-55)", "一般(55-40)", "低( <40)"};
    private static final String[] mArt5 = {"", "非常怪异(100-90)", "很怪异(90-75)", "较怪异(75-60)", "一般(60-50)", "普通( <50)"};
    private static final String[] mArt6 = {"", "宏大、极佳(100-85)", "很大、佳(85-75)", "较好、较佳(75-60)", "较小、一般(60-45)", "很小、不佳( <45)"};


    //    科学研究价值：
//    科教与科普价值:
//    典型性:
//    稀有性:
//    完整性:
//    资源容量:
    private static final String[] mScience1 = {"", "极高(100-90)", "很高(90-80)", "较高(80-70)", "一般(70-50)", "低(<50)"};
    private static final String[] mScience2 = {"", "极高(100-90)", "很高(90-80)", "较高(80-70)", "一般(70-50)", "低( <50)"};
    private static final String[] mScience3 = {"", "完整典型性(100-90)", "主要阶段(90-75)", "完整区域性(75-60)", "完整地区性(60-45)", "常见( <45)"};
    private static final String[] mScience4 = {"", "极奇特(100-90)", "很奇特(90-75)", "较奇特(75-60)", "普通(60-45)", "很普通( <45)"};
    private static final String[] mScience5 = {"", "极高(100-80)", "很高(80-60)", "较高(60-40)", "一般(40-0)", "低( 0 )"};
    private static final String[] mScience6 = {"", "极大(100-85)", "很大(85-75)", "较大(75-65)", "较小(65-50)", "很小( <50)"};

    private static final String[] mZiliaolaiyuan = {"", "出版的刊物", "区调报告", "科研报告", "专著", "论文", "实测"
            , "草测", "简测", "其他"};

    private static final String[] mJiaotongzhuangkuang = {"", "极佳", "良好", "一般", "差"};


    private void setViewData() {
        PaleontologicalaBean p = paleontoBeanList.get(0);
        tvBaseData1.setText(p.getRuins_dot());
        tvBaseData2.setText(p.getRelic_name());
        tvBaseData3.setText(relicType[Integer.parseInt(p.getRelic_type())]);
        setOkhttpNum(p.getArea(), tvBaseData4);
        setOkhttpNum(p.getUnit_id(), tvBaseData5);
        tvBaseData6.setText(p.getKaifaxianzhuang());
        tvBaseData7.setText(p.getBaohuxianzhuang());
        tvBaseData8.setText(p.getDistrict());
        tvBaseData9.setText(p.getKaifajianyi());
        tvBaseData10.setText(p.getBaohujianyi());
        tvBaseData11.setText(mJiaotongzhuangkuang[Integer.parseInt(p.getJiaotongzhuangkuang())]);
        tvBaseData12.setText(p.getArea());
        tvBaseData13.setText(p.getAltitude());
        //tvBaseData14.setText(p.getLevel());
        setOkhttpNum(p.getLevel(), tvBaseData14);
        tvBaseData15.setText(p.getFind_man());
        tvBaseData16.setText(p.getFind_time());
        tvBaseData17.setText(p.getDicengdaihao());
//        tvBaseData18.setText(p.getYanxing());
        setOkhttpNum(p.getYanxing(), tvBaseData18);
        tvBaseData19.setText(p.getYanxiang());
        tvBaseData20.setText(p.getMaicangzhuangtai());
        tvBaseData21.setText(p.getZhongleiname());

        tvScientific1.setText(mScience1[Integer.parseInt(p.getKexueyanjiujiazhi())]);
        tvScientific2.setText(mScience2[Integer.parseInt(p.getKejiaoyukepujiazhi())]);
        tvScientific3.setText(mScience3[Integer.parseInt(p.getTypicality())]);
        tvScientific4.setText(mScience4[Integer.parseInt(p.getRareness())]);
        tvScientific5.setText(mScience5[Integer.parseInt(p.getIntegrity())]);
        tvScientific6.setText(mScience6[Integer.parseInt(p.getResource_capacity())]);

        tvArt1.setText(mArt1[Integer.parseInt(p.getImage_value())]);
        tvArt2.setText(mArt2[Integer.parseInt(p.getTint_beauty())]);
        tvArt3.setText(mArt3[Integer.parseInt(p.getDongtaimei())]);
        tvArt4.setText(mArt4[Integer.parseInt(p.getYuyuemei())]);
        tvArt5.setText(mArt5[Integer.parseInt(p.getQitexingheqiyixing())]);
        tvArt6.setText(mArt6[Integer.parseInt(p.getZiyuanguimohezuhe())]);

        tvNature1.setText(mNature1[Integer.parseInt(p.getGeographical_condition())]);
        tvNature2.setText(mNature1[Integer.parseInt(p.getVulnerability())]);
        tvNature3.setText(mNature1[Integer.parseInt(p.getLvyou())]);
        tvNature4.setText(mNature1[Integer.parseInt(p.getSecurity())]);
        tvNature5.setText(mNature1[Integer.parseInt(p.getHuanjing_quality())]);

        tvDataSources1.setText(p.getZiliaolaiyuan());
//        tvDataSources2.setText(p.getSurvey_man());
        setOkhttpNum(p.getSurvey_man(), tvDataSources2);
        tvDataSources3.setText(p.getSurvey_time());
        tvDataSources4.setText(p.getRemark());
        setOkPacture(id);
        //
    }

    private void initPictureData(List<ImageBean> itemList) {
        if (itemList.isEmpty() || itemList.isEmpty()) {
            ivNgridLayout.setVisibility(View.GONE);
            ivOneimage.setVisibility(View.GONE);
        } else if (itemList.size() == 1) {
            ivNgridLayout.setVisibility(View.GONE);
            ivOneimage.setVisibility(View.VISIBLE);

            handlerOneImage(itemList.get(0));
        } else {
            ivNgridLayout.setVisibility(View.VISIBLE);
            ivOneimage.setVisibility(View.GONE);

            ivNgridLayout.setImagesData(itemList);
        }
    }

    private void handlerOneImage(ImageBean image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(ReLicDataActivity.this);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = ivOneimage.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        ivOneimage.setLayoutParams(layoutparams);
        ivOneimage.setClickable(true);
        ivOneimage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivOneimage.setImageUrl(image.getUrl());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                            } else if (status.equals("400")){
                                initToLogin(message);
                            }else{
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
    //有别的设备登录，返回登录页面
    private void initToLogin(String msg) {
        SharedUtils.putShare(context, "isLogin", false);
        ToastUtils.showLong(context, msg);
        ToActivityUtlis.toNextActivity(context, LoginActivity.class);
        ActivityCollectorUtils.finishAll();
    }
    private void setOkPacture(String id) {
        final String[] s1 = new String[1];
        OkGo.post(new Api(this).getgetImgUrl())
                .params("sessionId", sessionId)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("limeng", "s图片：" + s);
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            String message = js.optString("message");
                            String status = js.optString("status");
                            if (status.equals("200")) {
                                imagesList = new ArrayList<>();
                                JSONArray jsa = js.getJSONArray("message");
                                for (int i = 0; i < jsa.length(); i++) {
                                    String picture = new Api(context).getPictureDataUrl() + jsa.getJSONObject(i).get("name");
                                    imagesList.add(new ImageBean(picture, 500, 500));
                                    imagesList.add(new ImageBean(picture, 500, 500));
                                    imagesList.add(new ImageBean(picture, 500, 500));
                                    imagesList.add(new ImageBean(picture, 500, 500));
                                }
                                initPictureData(imagesList);
                            } else if (status.equals("400")){
                                initToLogin(message);
                            }else{
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

}
