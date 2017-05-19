package com.nandity.paleontology.relicdata.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nandity.paleontology.personneldata.PersonnelBean;
import com.nandity.paleontology.relicdata.ui.PaleontologicalActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于 古生物 Json 解析的帮助类
 *
 * Created by qingsong on 2017/5/7.
 */

public class PaleGsonHelper {

    public static List<PaleontologicalaBean> mPaleontoBeanList(String response){

        List<PaleontologicalaBean> mPaleontoBeanList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String dataArrayStr = jsonObject.getJSONObject("data").getString("data");
            Type type = new TypeToken<List<PaleontologicalaBean>>(){}.getType();
            Gson gson = new Gson();
            mPaleontoBeanList = gson.fromJson(dataArrayStr, type);
            return mPaleontoBeanList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mPaleontoBeanList;
    }
}
