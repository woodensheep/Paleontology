package com.nandity.paleontology.personneldata;

/**
 * Created by qingsong on 2017/5/18.
 */

public class PersonnelBean {
    private  String mName;
    private  String mCompany;
    private  String mMobile;
    private  String mIcon;
    private  String mPosition;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCompany() {
        return mCompany;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getmPosition() {
        return mPosition;
    }

    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public PersonnelBean(String mName, String mCompany, String mMobile, String mIcon, String mPosition) {
        this.mName = mName;
        this.mCompany = mCompany;
        this.mMobile = mMobile;
        this.mIcon = mIcon;
        this.mPosition = mPosition;
    }
}
