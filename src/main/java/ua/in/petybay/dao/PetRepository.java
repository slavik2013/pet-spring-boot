package ua.in.petybay.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.Pet;

/**
 * Created by slavik on 31.03.15.
 */

public interface PetRepository extends MongoRepository<Pet, String> {
}
