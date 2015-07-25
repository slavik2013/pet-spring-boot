package ua.in.petybay.entity;

import javax.validation.constraints.NotNull;

/**
 * Created by slavik on 05.04.15.
 */
public class Location {
    @NotNull
    String region;

    @NotNull
    String city;

    String detailAddress;

    public Location() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    @Override
    public String toString() {
        return "Location{" +
                "region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }
}
