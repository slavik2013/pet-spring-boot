package ua.in.petybay.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.Breed;

import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */
public interface BreedRepository extends MongoRepository<Breed, String> {
    List<Breed> findByCategoryName(String name);
    Breed findFirstByName(String name);
}
