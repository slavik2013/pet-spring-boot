package ua.in.petybay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection="advert")
public class Advert {

    public static enum STATE{
        ACTIVE,
        WAITING,
        NONACTIVE
    }

    @Id
    private String id;

    @DBRef
    @NotNull
    private List<Category> categories;

    @NotNull
    @Valid
    private Location location;
    private Price price;

    private Date publicationDate;
    @Size(min = 50, max = 4095)
    private String description;
    @NotNull
    @Size(min = 5, max = 70)
    private String title;

    private int viewCount;

    private String active;

    @NotNull
    @Valid
    private User user;

    @Size(max = 10)
    private List<ImageEntity> imageEntity;

    private STATE state;

    private String ipAddress;

    private int countPhone;
    private int countSkype;
    private int countFavorite;

    public Advert() {
    }

    public String getId() {
        return id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Location getLocation() {
        return location;
    }

    public Price getPrice() {
        return price;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getActive() {
        return active;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<ImageEntity> getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(List<ImageEntity> imageEntity) {
        this.imageEntity = imageEntity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCountPhone() {
        return countPhone;
    }

    public void setCountPhone(int countPhone) {
        this.countPhone = countPhone;
    }

    public int getCountSkype() {
        return countSkype;
    }

    public void setCountSkype(int countSkype) {
        this.countSkype = countSkype;
    }

    public int getCountFavorite() {
        return countFavorite;
    }

    public void setCountFavorite(int countFavorite) {
        this.countFavorite = countFavorite;
    }

    @Override
    public String toString() {
        return "Advert{" +
                "id='" + id + '\'' +
                ", categories=" + categories +
                ", location=" + location +
                ", price=" + price +
                ", publicationDate=" + publicationDate +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", viewCount=" + viewCount +
                ", active='" + active + '\'' +
                ", user=" + user +
                ", imageEntity=" + imageEntity +
                ", state=" + state +
                ", ipAddress='" + ipAddress + '\'' +
                ", countPhone=" + countPhone +
                ", countSkype=" + countSkype +
                ", countFavorite=" + countFavorite +
                '}';
    }

    public void calculateAndSetPublicationDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        this.setPublicationDate(new Date(cal.getTime().getTime()));
    }

    public static Builder getBuilder(){
        return new Advert().new Builder();
    }

    public class Builder{

        public Builder(){}

        public Builder category(List<Category> categories) {
            Advert.this.categories = categories;
            return this;
        }

        public Builder location(Location location) {
            Advert.this.location = location;
            return this;
        }

        public Builder price(Price price) {
            Advert.this.price = price;
            return this;
        }

        public Builder publicationDate(Date publicationDate) {
            Advert.this.publicationDate = publicationDate;
            return this;
        }

        public Builder description(String description) {
            Advert.this.description = description;
            return this;
        }

        public Builder title(String title) {
            Advert.this.title = title;
            return this;
        }

        public Builder viewCount(int viewCount) {
            Advert.this.viewCount = viewCount;
            return this;
        }

        public Builder active(String active) {
            Advert.this.active = active;
            return this;
        }

        public Builder imageEntity(List<ImageEntity> imageEntity) {
            Advert.this.imageEntity = imageEntity;
            return this;
        }

        public Advert build(){
            return Advert.this;
        }

    }
}
