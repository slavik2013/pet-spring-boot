package ua.in.petybay.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.Pet;

import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */

public interface PetRepository extends MongoRepository<Pet, String> {

    List<Pet> findByUserEmail(String userEmail);

    List<Pet> findByCategoryName(String categoryName);

    List<Pet> findByCategoryNameAndState(String categoryName, Pet.STATE state);

    List<Pet> findByUserEmailAndState(String userEmail, Pet.STATE state);
}
