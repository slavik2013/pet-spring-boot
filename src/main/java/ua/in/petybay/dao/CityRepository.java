package ua.in.petybay.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.City;

import java.util.List;

/**
 * Created by slavik on 26.07.15.
 */
public interface CityRepository extends MongoRepository<City, String> {
    City findOneByName(String name);
    List<City> findByRegionName(String regionName);
}
