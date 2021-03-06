package ua.in.petybay.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.in.petybay.entity.Category;

import java.util.List;

/**
 * Created by slavik on 31.03.15.
 */
public interface CategoryRepository extends MongoRepository<Category,String> {
    Category findFirstByName(String name);
    List<Category> findByLevel(int level);
    Long countByLevel(int level);
}
