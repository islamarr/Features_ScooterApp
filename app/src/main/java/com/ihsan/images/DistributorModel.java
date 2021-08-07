package com.ihsan.images;

public class DistributorModel {
    private String id, cityName, proId, fbLink, imgRes, phone, latLong;

    public DistributorModel(String id, String cityName, String proId, String fbLink, String imgRes, String phone, String latLong) {
        this.id = id;
        this.cityName = cityName;
        this.proId = proId;
        this.fbLink = fbLink;
        this.imgRes = imgRes;
        this.phone = phone;
        this.latLong = latLong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }
}
