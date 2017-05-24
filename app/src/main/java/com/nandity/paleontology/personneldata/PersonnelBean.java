package com.nandity.paleontology.personneldata;

/**
 * Created by qingsong on 2017/5/18.
 */

public class PersonnelBean {
    private  String id;
    private  String name;
    private  String iphone;
    private String position;
    private  String company;
    private  String photo;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public PersonnelBean(String id, String name, String iphone, String position, String company, String photo) {
        this.id = id;
        this.name = name;
        this.iphone = iphone;
        this.position = position;
        this.company = company;
        this.photo = photo;
    }
}
