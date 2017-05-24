package com.nandity.paleontology.personneldata;

/**
 * Created by qingsong on 2017/5/18.
 */

public class PersonnelBean {
    private  String name;
    private  String id;
    private  String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public PersonnelBean(String name, String mobile, String id) {
        this.name = name;
        this.id=id;
        this.mobile=mobile;
    }
}
