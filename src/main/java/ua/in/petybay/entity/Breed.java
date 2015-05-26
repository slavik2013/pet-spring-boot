package ua.in.petybay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection = "breed")
public class Breed {
    @JsonIgnore
    @Id
    String id;
//    @DBRef
    Category category;

    String name;
    String icon;

    public Breed() {
    }

    public Breed(Category category, String name, String icon) {
        this.category = category;
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "id='" + id + '\'' +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
