package ua.in.petybay.entity;

import lombok.Data;
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
@Data
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

}
