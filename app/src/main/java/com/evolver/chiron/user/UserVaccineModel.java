package com.evolver.chiron.user;

public class UserVaccineModel {
    String centerName;
    String centerAddress;
    String centerFromTiming;
    String centerToTiming;
    String feeType;
    String ageLimit;
    String vaccineName;
    String availableCapacity;

    public UserVaccineModel(String centerName, String centerAddress, String centerFromTiming, String centerToTiming, String feeType, String ageLimit, String vaccineName, String availableCapacity) {
        this.centerName = centerName;
        this.centerAddress = centerAddress;
        this.centerFromTiming = centerFromTiming;
        this.centerToTiming = centerToTiming;
        this.feeType = feeType;
        this.ageLimit = ageLimit;
        this.vaccineName = vaccineName;
        this.availableCapacity = availableCapacity;
    }
}
