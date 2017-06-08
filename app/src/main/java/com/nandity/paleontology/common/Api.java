package com.nandity.paleontology.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.nandity.paleontology.util.SharedUtils;

/**
 * Created by qingsong on 2017/5/18.
 * 183.230.108.112:9055
 */
public class Api {

    private String port;
    private String ip;

    public Api(Context context) {
        ip = (String) SharedUtils.getShare(context, "IP", "");
        port = (String) SharedUtils.getShare(context, "PORT", "");
    }

    //登录接口
    public String getLoginUrl() {

        return "http://" + ip + ":" + port + "/ael/android/login.do";
    }

    //人员数据接口
    public String getPersonnelUrl() {
        return "http://" + ip + ":" + port + "/ael/android/getPerson.do";
    }

    //更新app
    public String getUpdateServiceUrl() {
        return "http://" + ip + ":" + port + "/ael/android/download.do";
    }

    //    获取版本号
    public String getUpdateVerCodeUrl() {
        return "http://" + ip + ":" + port + "/ael/android/haveNewVersion.do";
    }
    //古生物数据接口
    public String getPaleontogicalUrl() {
        return "http://" + ip + ":" + port + "/ael/android/getPalae.do";
    }
    //古生物详细数据接口
    public String getPalaeInfoUrl() {
        return "http://" + ip + ":" + port + "/ael/android/getPalaeInfo.do";
    }
    //古生物详细数据接口-id对应的文字接口
    public String getfindStaticValueDataUrl() {
        return "http://" + ip + ":" + port + "/ael/android/findStaticValueData.do";
    }
    //古生物详细数据图片接口
    public String getgetImgUrl() {
        return "http://" + ip + ":" + port + "/ael/android/getImg.do";
    }

    //图片接口
    public String getPictureDataUrl() {
        return "http://" + ip + ":" + port + "/ael/upload_file/img/";
    }

    //古生物详细数据接口
    public String getFossilUrl() {
        return "http://" + ip + ":" + port + "/ael/android/getFossil.do";
    }

    //古生物详细数据接口- 所属区县
    public String getfindAreaDataUrl() {
        return "http://" + ip + ":" + port + "/ael/android/findAreaData.do";
    }


}
