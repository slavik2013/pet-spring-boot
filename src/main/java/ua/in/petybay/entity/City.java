package ua.in.petybay.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by slavik on 25.07.15.
 */
@Document(collection = "city")
@Data
public class City {

    @Id
    private String id;

    @NotNull
    private String name;

    private List<Title> titles;

    private double lat;
    private double lon;

    private String regionName;

}
