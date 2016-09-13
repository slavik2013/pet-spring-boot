package ua.in.petybay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by slavik on 25.07.15.
 */
@Document(collection = "region")
public class Region {

    @Id
    private String id;

    @NotNull
    private String name;

    private List<Title> titles;

    @DBRef
    private List<City> cities;

    private List<String> citiesNames;

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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<String> getCitiesNames() {
        return citiesNames;
    }

    public void setCitiesNames(List<String> citiesNames) {
        this.citiesNames = citiesNames;
    }
}
