package ua.in.petybay.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection = "category")
@Data
public class Category {

    @Transient
    public static final int TOP_LEVEL = 1;

    @Id
    private String id;
    private String name;
    private String icon;
    private List<Title> titles;
    private AdOptions adOptions;
    private long countActive;
    private long countNonActive;
    private long countWaiting;

    int level;

    private String parent;

    @DBRef
    private List<Category> childs;

    int displayOrder;

}
