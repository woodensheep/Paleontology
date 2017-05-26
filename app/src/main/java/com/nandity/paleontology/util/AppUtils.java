package com.nandity.paleontology.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * Created by qingsong on 2017/5/24.
 */

public class AppUtils {
    /**
     * 检测当前版本
     *
     * @param context
     * @return
     */
    public static String  getVerCode(Context context) {
        int versionNumber = -1;
        try {
            versionNumber = context.getPackageManager().getPackageInfo("com.nandity.paleontology", 0).versionCode;
            System.out.println("当前版本" + versionNumber);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("version", e.getMessage());
        }
        return versionNumber+"";
    }
    /**
     * 检测当前版本name
     *
     * @param context
     * @return
     */
    public static String  getVerName(Context context) {
        String versionName="";
        try {
            String pkName = context.getPackageName();
            versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("version", e.getMessage());
        }
        return versionName;
    }
    /**
     * apk自动安装
     * @param context
     * @param
     */
    public void openFile( Context context ) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile( new File("/sdcard/Download/paleontology.apk")); //这里是APK路径
        intent.setDataAndType( uri , "application/vnd.android.package-archive" ) ;
        context.startActivity(intent);
    }

}
