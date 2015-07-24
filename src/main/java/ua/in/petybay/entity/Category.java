package ua.in.petybay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection = "category")
public class Category {

    @Transient
    public static final int TOP_LEVEL = 1;

    public static class Title {
        String language;
        String title;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "Title{" +
                    "language='" + language + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

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

    String parent;

    @DBRef
    private List<Category> childs;

    int displayOrder;

    public Category() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AdOptions getAdOptions() {
        return adOptions;
    }

    public void setAdOptions(AdOptions adOptions) {
        this.adOptions = adOptions;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
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

    public List<Category> getChilds() {
        return childs;
    }

    public void setChilds(List<Category> childs) {
        this.childs = childs;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", titles=" + titles +
                ", adOptions=" + adOptions +
                ", countActive=" + countActive +
                ", countNonActive=" + countNonActive +
                ", countWaiting=" + countWaiting +
                ", level=" + level +
                ", parent='" + parent + '\'' +
                ", childs=" + childs +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
