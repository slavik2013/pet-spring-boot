package ua.in.petybay.entity;

import java.util.List;

/**
 * Created by slavik on 25.07.15.
 */
public class City {

    String id;

    String name;

    List<Title> titles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", titles=" + titles +
                '}';
    }
}
