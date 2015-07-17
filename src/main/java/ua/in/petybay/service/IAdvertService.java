package ua.in.petybay.service;

import ua.in.petybay.entity.AdvertVerificationToken;
import ua.in.petybay.entity.Pet;

/**
 * Created by slavik on 18.07.15.
 */
public interface IAdvertService {

    void createVerificationToken(Pet advert, String token);

    AdvertVerificationToken getVerificationToken(String advertVerificationToken);
}
