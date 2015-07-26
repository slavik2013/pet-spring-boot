package ua.in.petybay.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.Region;

/**
 * Created by slavik on 25.07.15.
 */
public interface RegionRepository extends MongoRepository<Region, String> {
    Region findOneByName(String name);
}
