package ua.in.petybay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection = "category")
public class Category {

    @Id @JsonIgnore
    String id;
    String name;
    String icon;

    public Category() {
    }

    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
