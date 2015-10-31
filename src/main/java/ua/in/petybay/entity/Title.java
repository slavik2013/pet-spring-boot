package ua.in.petybay.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by slavik on 25.07.15.
 */
@Data
@XmlRootElement(name = "title")
@XmlAccessorType(XmlAccessType.FIELD)
public class Title {
    private String language;
    private String title;
}
