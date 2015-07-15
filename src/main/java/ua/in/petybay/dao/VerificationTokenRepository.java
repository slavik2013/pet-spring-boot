package ua.in.petybay.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.in.petybay.entity.User;
import ua.in.petybay.entity.VerificationToken;

/**
 * Created by slavik on 14.07.15.
 */
public interface VerificationTokenRepository extends PagingAndSortingRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
