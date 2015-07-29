package ua.in.petybay.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by slavik on 20.07.15.
 */
@Document(collection = "option")
@Data
public class Option {
    @Id
    private String id;

    private String title;
    private boolean isRequired;

    private int intValue;
    private String stringValue;
    private double doubleValue;
    private boolean booleanValue;

    private List<String> stringList;
    private List<Integer> intList;

}

