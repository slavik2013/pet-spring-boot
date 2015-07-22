package ua.in.petybay.service;

import org.springframework.data.domain.Pageable;
import ua.in.petybay.entity.Advert;
import ua.in.petybay.entity.AdvertVerificationToken;
import ua.in.petybay.entity.Category;

import java.util.List;

/**
 * Created by slavik on 18.07.15.
 */
public interface IAdvertService {

    void createVerificationToken(Advert advert, String token);

    AdvertVerificationToken getVerificationToken(String advertVerificationToken);

    List<Category> addCategoriesToNewAdvert(Advert advert);

    Advert save(Advert advert);

    List<Advert> findByUserEmail(String userEmail);

    List<Advert> findByCategoryName(String categoryName);

    List<Advert> findByCategoryNameAndState(String categoryName, Advert.STATE state);

    List<Advert> findByUserEmailAndState(String userEmail, Advert.STATE state);

    List<Advert> findByCategoryNameAndState(String categoryName, Advert.STATE state, Pageable pageable);

    Long countByCategoryNameAndState(String categoryName, Advert.STATE state);

    Advert findOne(String id);

    List<Advert> findAll();
}
