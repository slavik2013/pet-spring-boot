package ua.in.petybay.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ua.in.petybay.entity.Breed;

import java.util.List;

/**
 * Created by slavik on 05.04.15.
 */
@Repository
public class BreedDao {

    @Autowired
    private MongoOperations mongoOperations;

    public List<Breed> getBreedByCategoryName(String categoryName){
        List<Breed> breeds = null;
        breeds = mongoOperations.find(Query.query(Criteria.where("category.name").is(categoryName)), Breed.class);
        return breeds;
    }
}
