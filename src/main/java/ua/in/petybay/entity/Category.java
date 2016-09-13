package ua.in.petybay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection = "category")
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
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
    private int level;
    private String parent;

    @DBRef
    private List<Category> childs;
    private int displayOrder;

    public static int getTopLevel() {
        return TOP_LEVEL;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public AdOptions getAdOptions() {
        return adOptions;
    }

    public void setAdOptions(AdOptions adOptions) {
        this.adOptions = adOptions;
    }

    public long getCountActive() {
        return countActive;
    }

    public void setCountActive(long countActive) {
        this.countActive = countActive;
    }

    public long getCountNonActive() {
        return countNonActive;
    }

    public void setCountNonActive(long countNonActive) {
        this.countNonActive = countNonActive;
    }

    public long getCountWaiting() {
        return countWaiting;
    }

    public void setCountWaiting(long countWaiting) {
        this.countWaiting = countWaiting;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Category> getChilds() {
        return childs;
    }

    public void setChilds(List<Category> childs) {
        this.childs = childs;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
