package ua.in.petybay.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.Advert;

import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */

public interface AdvertRepository extends MongoRepository<Advert, String> {

    List<Advert> findByUserEmail(String userEmail);
//
//    List<Advert> findByCategoryName(String categoryName);
//
//    List<Advert> findByCategoryNameAndState(String categoryName, Advert.STATE state);
//
    List<Advert> findByUserEmailAndState(String userEmail, Advert.STATE state);
//
//    Page<Advert> findByCategoryNameAndState(String userEmail, Advert.STATE state, Pageable pageable);
//
//    Long countByCategoryNameAndState(String categoryName, Advert.STATE state);

    Page<Advert> findByLocationRegionNameAndLocationCityNameAndState(String regionName, String cityName, Advert.STATE state, Pageable pageable);
}
