package ua.in.petybay.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by slavik on 20.07.15.
 */
@Document(collection = "option")
@Data
@XmlRootElement(name = "option")
@XmlAccessorType(XmlAccessType.FIELD)
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

