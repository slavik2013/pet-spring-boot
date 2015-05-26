package ua.in.petybay.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.Owner;

/**
 * Created by slavik on 31.03.15.
 */
public interface OwnerRepository extends MongoRepository<Owner, String> {
}
