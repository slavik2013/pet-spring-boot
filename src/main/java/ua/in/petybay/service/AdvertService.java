package ua.in.petybay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.in.petybay.dao.AdvertVerificationTokenRepository;
import ua.in.petybay.dao.PetRepository;
import ua.in.petybay.entity.AdvertVerificationToken;
import ua.in.petybay.entity.Pet;

/**
 * Created by slavik on 18.07.15.
 */
@Component
public class AdvertService implements IAdvertService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    private AdvertVerificationTokenRepository tokenRepository;

    @Override
    public void createVerificationToken(Pet advert, String token) {
        AdvertVerificationToken myToken = new AdvertVerificationToken(token, advert);
        tokenRepository.save(myToken);
    }

    @Override
    public AdvertVerificationToken getVerificationToken(String advertVerificationToken) {
        return tokenRepository.findByToken(advertVerificationToken);
    }

}
