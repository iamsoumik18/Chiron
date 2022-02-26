package com.evolver.chiron;

public class Model {

    String hospital;
    String bed;

    public Model(){

    }

    public Model(String hospital, String bed) {
        this.hospital = hospital;
        this.bed = bed;
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
}
