package com.evolver.chiron.user;

public class UserModel {

    String hospital;
    String bed;
    String price;
    String Phone;
    String facilites;
    String Addres;


    public UserModel(){

    }

    public UserModel(String hospital, String bed, String price, String Phone, String facilites, String Addres ) {
        this.hospital = hospital;
        this.bed = bed;
        this.price = price;
        this.Addres = Addres;
        this.facilites = facilites;
        this.Phone = Phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFacilites() {
        return facilites;
    }

    public void setFacilites(String facilites) {
        this.facilites = facilites;
    }

    public String getAddres() {
        return Addres;
    }

    public void setAddres(String addres) {
        Addres = addres;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
