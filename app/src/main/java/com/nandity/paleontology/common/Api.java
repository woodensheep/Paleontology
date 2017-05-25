package com.nandity.paleontology.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.nandity.paleontology.util.SharedUtils;

/**
 * Created by qingsong on 2017/5/18.
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

    //古生物数据接口
    public String getPaleontogicalUrl() {
        return "http://" + ip + ":" + port + "/ael/android/getPalae.do";
    }
    //古生物详细数据接口
    public String getPalaeInfoUrl() {
        return "http://" + ip + ":" + port + "/ael/android/getPalaeInfo.do";
    }
    //古生物详细数据接口
    public String getfindStaticValueDataUrl() {
        return "http://" + ip + ":" + port + "/ael/android/findStaticValueData.do";
    }


    public static final String DATE_Personnel = "";
}
