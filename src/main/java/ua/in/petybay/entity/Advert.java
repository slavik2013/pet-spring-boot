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
    @Size(max = 5)
    private List<Category> categories;

    @NotNull
    @Valid
    private Location location;

    @Valid
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


    public void calculateAndSetPublicationDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        this.setPublicationDate(new Date(cal.getTime().getTime()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ImageEntity> getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(List<ImageEntity> imageEntity) {
        this.imageEntity = imageEntity;
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
}
