package com.nandity.paleontology.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingsong on 2017/5/26.
 */

public class ActivityCollectorUtils {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
