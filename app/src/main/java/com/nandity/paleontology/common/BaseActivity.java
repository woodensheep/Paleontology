package com.nandity.paleontology.common;

import android.app.Activity;
import android.os.Bundle;

import com.nandity.paleontology.util.ActivityCollectorUtils;

/**
 * Created by qingsong on 2017/5/26.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ActivityCollectorUtils.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}

