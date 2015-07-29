package ua.in.petybay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by slavik on 14.07.15.
 */
@Document(collection="user")
@Data
public class User {

    @Id @JsonIgnore
    private String id;

    @NotBlank
    @Email
    private String email;

    @Field("name")
    @NotBlank
    private String name;

    private String phone;

    @Size(max = 100)
    private String skype;
//    @JsonIgnore
    private String password;
    @JsonIgnore
    private List<String> authority;
    private Date dateCreate;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

}
