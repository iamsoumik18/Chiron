package com.evolver.chiron.user;

public class UserModel {

    String Hospital;
    String BedCount;
    String PricePerBed;
    String PhoneNo;
    String Facilities;
    String Address;


    public UserModel(){

    }

    public UserModel(String Address, String BedCount, String Facilities, String Hospital, String PhoneNo, String PricePerBed) {
        this.Hospital = Hospital;
        this.BedCount = BedCount;
        this.PricePerBed = PricePerBed;
        this.Address = Address;
        this.Facilities = Facilities;
        this.PhoneNo = PhoneNo;
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

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getFacilities() {
        return Facilities;
    }

    public void setFacilities(String facilities) {
        Facilities = facilities;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
