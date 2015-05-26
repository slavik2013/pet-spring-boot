package ua.in.petybay.entity;

/**
 * Created by slavik on 24.04.15.
 */
public class ImageEntity {

    String id;
    String htmlLink;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id='" + id + '\'' +
                ", htmlLink='" + htmlLink + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
