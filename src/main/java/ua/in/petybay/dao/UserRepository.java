package ua.in.petybay.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.in.petybay.entity.User;

/**
 * Created by slavik on 14.07.15.
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByName(String name);
    User findByEmail(String email);
}
