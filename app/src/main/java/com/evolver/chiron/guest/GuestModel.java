package com.evolver.chiron.guest;

public class GuestModel {

    String Hospital;
    String BedCount;
    String PricePerBed;

    public GuestModel(){

    }

    public GuestModel(String Hospital, String BedCount, String PricePerBed) {
        this.Hospital = Hospital;
        this.BedCount = BedCount;
        this.PricePerBed = PricePerBed;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public String getBedCount() {
        return BedCount;
    }

    public void setBedCount(String bedCount) {
        BedCount = bedCount;
    }

    public String getPricePerBed() {
        return PricePerBed;
    }

    public void setPricePerBed(String pricePerBed) {
        PricePerBed = pricePerBed;
    }
}
