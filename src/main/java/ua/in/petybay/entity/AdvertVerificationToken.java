package ua.in.petybay.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by slavik on 18.07.15.
 */
@Document(collection = "advertverificationtoken")
@Data
public class AdvertVerificationToken {
    @Id
    private String id;

    private String token;

    @DBRef
    private Advert advert;

    private Date expiryDate;

    private boolean verified;

    public AdvertVerificationToken() {
    }

    public AdvertVerificationToken(String token, Advert advert) {
        this.token = token;
        this.advert = advert;
    }


}
