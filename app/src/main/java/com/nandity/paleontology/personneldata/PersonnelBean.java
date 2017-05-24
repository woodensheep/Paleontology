package com.nandity.paleontology.personneldata;

/**
 * Created by qingsong on 2017/5/18.
 */

public class PersonnelBean {
    private  String id;
    private  String name;
    private  String iphone;

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

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


    public PersonnelBean(String id, String name, String iphone) {
        this.id = id;
        this.name = name;
        this.iphone = iphone;
    }
}
