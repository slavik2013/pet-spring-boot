package ua.in.petybay.entity;

import javax.validation.constraints.NotNull;

/**
 * Created by slavik on 05.04.15.
 */
public class Location {
    @NotNull
    Region region;

    @NotNull
    City city;

    String detailAddress;

    public Location() {
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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
                "region=" + region +
                ", city=" + city +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }
}
