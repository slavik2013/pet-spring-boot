package ua.in.petybay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by slavik on 18.07.15.
 */
@Document(collection = "advertverificationtoken")
public class AdvertVerificationToken {
    @Id
    private String id;

    private String token;

    @DBRef
    private Pet advert;

    private Date expiryDate;

    private boolean verified;

    public AdvertVerificationToken() {
    }

    public AdvertVerificationToken(String token, Pet advert) {
        this.token = token;
        this.advert = advert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Pet getPet() {
        return advert;
    }

    public void setPet(Pet advert) {
        this.advert = advert;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "AdvertVerificationToken{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", advert=" + advert +
                ", expiryDate=" + expiryDate +
                ", verified=" + verified +
                '}';
    }
}
