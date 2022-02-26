package com.evolver.chiron;

public class GuestModel {

    String hospital;
    String bed;
    String price;

    public GuestModel(){

    }

    public GuestModel(String hospital, String bed, String price) {
        this.hospital = hospital;
        this.bed = bed;
        this.price = price;
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
