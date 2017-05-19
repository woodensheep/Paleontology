package com.nandity.paleontology.personneldata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于 Json 解析的帮助类
 *
 * Created by qingsong on 2017/5/7.
 */

public class PerGsonHelper {

    public static List<PersonnelBean> getPersonnelBeanList(String response){

        List<PersonnelBean> mPersonnelBeanList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String dataArrayStr = jsonObject.getJSONObject("data").getString("data");
            Type type = new TypeToken<List<PersonnelBean>>(){}.getType();
            Gson gson = new Gson();
            mPersonnelBeanList = gson.fromJson(dataArrayStr, type);
            return mPersonnelBeanList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mPersonnelBeanList;
    }
}
