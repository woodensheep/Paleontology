package com.nandity.paleontology.common;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.SdkConstants;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;

/**
 * Created by qingsong on 2017/5/18.
 */

public class MyApplication extends Application {
    private static final String TAG = "AliyunApplication";
    private static final int Time=10000;
    @Override
    public void onCreate() {
        super.onCreate();
        initOneSDK(this);
        //必须调用初始化
        OkGo.init(this);
        try {
            OkGo.getInstance()
                    .debug("OkGo")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(Time)  //全局的连接超时时间
                    .setReadTimeOut(Time)     //全局的读取超时时间
                    .setWriteTimeOut(Time)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *
     *
     * @param applicationContext
     */
    private void initOneSDK(final Context applicationContext) {
        Log.w(TAG, "get App Package Name: " + applicationContext.getPackageName());

        AlibabaSDK.asyncInit(applicationContext, new InitResultCallback() {

            public void onSuccess() {
                Log.d(TAG, "init onesdk success");
                //alibabaSDK初始化成功后，初始化移动推送通道
                initCloudChannel(applicationContext);
            }

            public void onFailure(int code, String message) {
                Log.e(TAG, "init onesdk failed : " + message);
            }
        });
    }

    /**
     *
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        final CloudPushService pushService = AlibabaSDK.getService(CloudPushService.class);
        pushService.setLogLevel(3);
        pushService.register(applicationContext, new CommonCallback() {
            public void onSuccess() {
                Log.i(TAG, "init CloudPushService success, device id: " + pushService.getDeviceId() +
                        ", UtDid: " + pushService.getUTDeviceId() +
                        ", Appkey: " + AlibabaSDK.getGlobalProperty(SdkConstants.APP_KEY));
            }

            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed. errorcode:" + errorCode + ", errorMessage:" + errorMessage);
            }
        });


    }
}
