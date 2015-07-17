package ua.in.petybay.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.in.petybay.entity.AdvertVerificationToken;
import ua.in.petybay.entity.Pet;

/**
 * Created by slavik on 18.07.15.
 */
public interface AdvertVerificationTokenRepository extends PagingAndSortingRepository<AdvertVerificationToken, String> {
    AdvertVerificationToken findByToken(String token);

    AdvertVerificationToken findByPet(Pet advert);
}
