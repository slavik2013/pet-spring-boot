package ua.in.petybay.entity;

/**
 * Created by slavik on 05.04.15.
 */
public class Location {
    String region;
    String city;

    public Location() {
    }

    public Location(String region, String city) {
        this.region = region;
        this.city = city;
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

    @Override
    public String toString() {
        return "Location{" +
                "region='" + region + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
