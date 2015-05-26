package ua.in.petybay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.in.petybay.dao.BreedRepository;
import ua.in.petybay.dao.CategoryRepository;
import ua.in.petybay.dao.OwnerRepository;
import ua.in.petybay.dao.PetRepository;
import ua.in.petybay.entity.Breed;
import ua.in.petybay.entity.Category;
import ua.in.petybay.entity.Pet;

/**
 * Created by slavik on 05.04.15.
 */
@Service
public class PetService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BreedRepository breedRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;


    public boolean save(Pet pet){
        Category category = categoryRepository.findFirstByName(pet.getCategory().getName());
        Breed breed = breedRepository.findFirstByName(pet.getBreed().getName());
        pet.setCategory(category);
        pet.setBreed(breed);
        petRepository.save(pet);
        return true;
    }
}
