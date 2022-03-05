package com.evolver.chiron.user.ModelCovidTracker.ModelCountriesAll;

public class CountryInfo {
    String flag;
    String countryname;

    public CountryInfo(String flag, String countryname) {
        this.flag = flag;
        this.countryname = countryname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }
}
