package com.nandity.paleontology.personneldata;

/**
 * Created by qingsong on 2017/5/18.
 */

public class PersonnelBean {
    private  String mName;
    private  String id;


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public PersonnelBean(String mName, String mCompany, String mMobile, String mIcon, String mPosition) {
        this.mName = mName;
    }
}
