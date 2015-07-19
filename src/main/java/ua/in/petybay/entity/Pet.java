package ua.in.petybay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection="pet")
public class Pet {

    public static enum STATE{
        ACTIVE,
        WAITING,
        NONACTIVE
    }

    @Id
    private String id;

//    @DBRef
    @NotNull
    private Category category;

    private String color;
    private Date birthday;
    private String gender;
    private String documents;
    private String parentsTitle;
    @NotNull
    @Valid
    private Location location;
    private Price price;

//    @DBRef
    private Breed breed;

//    @DBRef
    private Owner owner;
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

    public Pet() {
    }

    public Pet(String id, Category category, String color, Date birthday, String gender, String documents, String parentsTitle, Location location, Price price, Breed breed, Owner owner, Date publicationDate, String description, String title, int viewCount, String active, User user, List<ImageEntity> imageEntity, STATE state) {
        this.id = id;
        this.category = category;
        this.color = color;
        this.birthday = birthday;
        this.gender = gender;
        this.documents = documents;
        this.parentsTitle = parentsTitle;
        this.location = location;
        this.price = price;
        this.breed = breed;
        this.owner = owner;
        this.publicationDate = publicationDate;
        this.description = description;
        this.title = title;
        this.viewCount = viewCount;
        this.active = active;
        this.user = user;
        this.imageEntity = imageEntity;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getDocuments() {
        return documents;
    }

    public String getParentsTitle() {
        return parentsTitle;
    }

    public Location getLocation() {
        return location;
    }

    public Price getPrice() {
        return price;
    }

    public Breed getBreed() {
        return breed;
    }

    public Owner getOwner() {
        return owner;
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public void setParentsTitle(String parentsTitle) {
        this.parentsTitle = parentsTitle;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    @Override
    public String toString() {
        return "Pet{" +
                "id='" + id + '\'' +
                ", category=" + category +
                ", color='" + color + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", documents='" + documents + '\'' +
                ", parentsTitle='" + parentsTitle + '\'' +
                ", location=" + location +
                ", price=" + price +
                ", breed=" + breed +
                ", owner=" + owner +
                ", publicationDate=" + publicationDate +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", viewCount=" + viewCount +
                ", active='" + active + '\'' +
                ", user=" + user +
                ", imageEntity=" + imageEntity +
                '}';
    }

    public void calculateAndSetPublicationDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        this.setPublicationDate(new Date(cal.getTime().getTime()));
    }

    public static Builder getBuilder(){
        return new Pet().new Builder();
    }

    public class Builder{

        public Builder(){}

        public Builder category(Category category) {
            Pet.this.category = category;
            return this;
        }

        public Builder color(String color) {
            Pet.this.color = color;
            return this;
        }

        public Builder birthday(Date birthday) {
            Pet.this.birthday = birthday;
            return this;
        }

        public Builder gender(String gender) {
            Pet.this.gender = gender;
            return this;
        }

        public Builder documents(String documents) {
            Pet.this.documents = documents;
            return this;
        }

        public Builder parentsTitle(String parentsTitle) {
            Pet.this.parentsTitle = parentsTitle;
            return this;
        }

        public Builder location(Location location) {
            Pet.this.location = location;
            return this;
        }

        public Builder price(Price price) {
            Pet.this.price = price;
            return this;
        }

        public Builder breed(Breed breed) {
            Pet.this.breed = breed;
            return this;
        }

        public Builder owner(Owner owner) {
            Pet.this.owner = owner;
            return this;
        }

        public Builder publicationDate(Date publicationDate) {
            Pet.this.publicationDate = publicationDate;
            return this;
        }

        public Builder description(String description) {
            Pet.this.description = description;
            return this;
        }

        public Builder title(String title) {
            Pet.this.title = title;
            return this;
        }

        public Builder viewCount(int viewCount) {
            Pet.this.viewCount = viewCount;
            return this;
        }

        public Builder active(String active) {
            Pet.this.active = active;
            return this;
        }

        public Builder imageEntity(List<ImageEntity> imageEntity) {
            Pet.this.imageEntity = imageEntity;
            return this;
        }

        public Pet build(){
            return Pet.this;
        }

    }
}
