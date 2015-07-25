package ua.in.petybay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ua.in.petybay.dao.AdvertRepository;
import ua.in.petybay.dao.AdvertVerificationTokenRepository;
import ua.in.petybay.dao.CategoryRepository;
import ua.in.petybay.entity.Advert;
import ua.in.petybay.entity.AdvertVerificationToken;
import ua.in.petybay.entity.Category;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by slavik on 18.07.15.
 */
@Repository
public class AdvertService implements IAdvertService {

    @Autowired
    private AdvertVerificationTokenRepository tokenRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void createVerificationToken(Advert advert, String token) {
        AdvertVerificationToken myToken = new AdvertVerificationToken(token, advert);
        tokenRepository.save(myToken);
    }

    @Override
    public AdvertVerificationToken getVerificationToken(String advertVerificationToken) {
        return tokenRepository.findByToken(advertVerificationToken);
    }


    public List<Category> addCategoriesToNewAdvert(Advert advert){
        Category category = categoryRepository.findFirstByName(advert.getCategories().get(0).getName());

        List<Category> categories = new LinkedList<Category>();

        categories.add(category);

//        while(category.getParent() != null){
//            categories.add(category);
//            category = category.getParent();
//        }

        while(category.getLevel() != 1){
            category = mongoOperations.findOne(Query.query(Criteria.where("childs").in(category)), Category.class);
            categories.add(category);
        }


        advert.setCategories(categories);

        return categories;
    }

    public Advert save(Advert advert){
        return advertRepository.save(advert);
    }

    @Override
    public List<Advert> findByUserEmail(String userEmail){
        return advertRepository.findByUserEmail(userEmail);
    }

    @Override
    public List<Advert> findByCategoryName(String categoryName){
        Category category = categoryRepository.findFirstByName(categoryName);
        return mongoOperations.find(Query.query(Criteria.where("categories").in(category)), Advert.class);
    }

    @Override
    public List<Advert> findByCategoryNameAndState(String categoryName, Advert.STATE state){
        Category category = categoryRepository.findFirstByName(categoryName);
        return mongoOperations.find(Query.query(Criteria.where("categories").in(category.getId())), Advert.class);
    }

    @Override
    public List<Advert> findByUserEmailAndState(String userEmail, Advert.STATE state){
        return advertRepository.findByUserEmailAndState(userEmail, state);
    }

    @Override
    public List<Advert> findByCategoryNameAndState(String categoryName, Advert.STATE state, Pageable pageable){
        Category category = categoryRepository.findFirstByName(categoryName);
        return mongoOperations.find(Query.query(Criteria.where("categories").in(category.getId()).and("state").is(state)).skip(pageable.getOffset()).limit(pageable.getPageSize()), Advert.class);
    }

    @Override
    public Long countByCategoryNameAndState(String categoryName, Advert.STATE state){return null;}

    @Override
    public Advert findOne(String id){
        return advertRepository.findOne(id);
    }

    @Override
    public List<Advert> findAll(){
        return advertRepository.findAll();
    }
}
