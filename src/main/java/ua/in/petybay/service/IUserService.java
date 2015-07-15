package ua.in.petybay.service;

import ua.in.petybay.entity.User;
import ua.in.petybay.entity.VerificationToken;

import java.lang.String; /**
 * Created by slavik on 14.07.15.
 */
public interface IUserService {

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    boolean emailExist(String email);
}
