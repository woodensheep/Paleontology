package com.nandity.paleontology.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.nandity.paleontology.MainActivity;

/**
 * Created by qingsong on 2017/5/17.
 */

public class ToActivityUtlis {


    /**
     * @Description: 跳转
     * @param packageContext
     *            from,一般传XXXActivity.this
     * @param cls
     *            to,一般传XXXActivity.class
     */
    public static void toNextActivity(Context packageContext, Class<?> cls) {
        Intent i = new Intent(packageContext, cls);
        packageContext.startActivity(i);
    }
}
