package ua.in.petybay.entity;

import javax.validation.constraints.NotNull;

/**
 * Created by slavik on 05.04.15.
 */
public class Location {
    @NotNull
    private Region region;

    @NotNull
    private City city;

    private String detailAddress;

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
}
