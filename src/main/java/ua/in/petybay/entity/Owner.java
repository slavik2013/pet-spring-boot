package ua.in.petybay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by slavik on 31.03.15.
 */
@Document(collection="owner")
public class Owner {

    @Id @JsonIgnore
    String id;
    String email;
    String name;
    String phone;
    String skype;
    String password;
    Date dateCreate;

    public Owner() {
    }

    public Owner(String email, String name, String phone, String skype, String password, Date dateCreate) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.skype = skype;
        this.password = password;
        this.dateCreate = dateCreate;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSkype() {
        return skype;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", skype='" + skype + '\'' +
                ", password='" + password + '\'' +
                ", dateCreate=" + dateCreate +
                '}';
    }
}
