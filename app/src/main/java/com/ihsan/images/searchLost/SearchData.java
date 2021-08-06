package com.ihsan.images.searchLost;

public class SearchData {
    private String name;
    private String plateNumber;
    private String phoneNumber;

    public SearchData() { }

    public SearchData(String name, String plateNumber, String phoneNumber) {
        this.name = name;
        this.plateNumber = plateNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
