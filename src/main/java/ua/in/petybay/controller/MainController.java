package ua.in.petybay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.in.petybay.dao.BreedRepository;
import ua.in.petybay.dao.CategoryRepository;
import ua.in.petybay.dao.PetRepository;
import ua.in.petybay.entity.Breed;
import ua.in.petybay.entity.Category;
import ua.in.petybay.entity.ImageEntity;
import ua.in.petybay.entity.Pet;
import ua.in.petybay.service.PetService;
import ua.in.petybay.service.image.picasa.PicasaImageSaver;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;


/**
 * Created by slavik on 31.03.15.
 */
@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private PetRepository petRepository;
//    @Autowired
//    private OwnerRepository ownerRepository;
    @Autowired
    private BreedRepository breedRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private PetService petService;

    @Autowired
    private PicasaImageSaver picasaImageSaver;


    @RequestMapping(value = "/pet", method = RequestMethod.POST, produces = "text/plain")
//    @Secured({"ROLE_ADMIN"})
    public String savePet(@RequestBody Pet pet){
        System.out.println("pet : " + pet);
        pet.calculateAndSetPublicationDate();
        petService.save(pet);

        return "all is ok";
    }



    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET, produces = "application/json")
    public Pet getPet(@PathVariable("petId") String petId){
        System.out.println("getPet() petId = " + petId);
        Pet pet = null;
        if(petId != null && !"".equals(petId)){
            pet = petRepository.findOne(petId);
            pet.setViewCount(pet.getViewCount() + 1);
            petRepository.save(pet);
        }
        return pet;
    }

    @RequestMapping(value = "/pet", method = RequestMethod.GET, produces = "application/json")
    public List<Pet> getPets(){
        System.out.println("getPets()");
        return  petRepository.findAll();
    }


    @RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
    public List<Category> getCategories(){
        System.out.println("getCategories()");
        return  categoryRepository.findAll();
    }

    @RequestMapping(value = "/breeds/{categoryName}",method = RequestMethod.GET)
    public List<Breed> getBreeds(@PathVariable("categoryName") String categoryName){
        System.out.println("categoryName = " + categoryName);
        return breedRepository.findByCategoryName(categoryName);
    }

    @RequestMapping(value = "/text", method = RequestMethod.GET)
    public String getText(){
        System.out.println("call /text time = " + System.currentTimeMillis());

        LocaleContextHolder.getLocale();
        return "thread name = " +  Thread.currentThread().getName();
    }

    @RequestMapping(value = "/locale", method = RequestMethod.GET)
    public String getLocale(HttpServletRequest request){
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            System.out.println("ipAddress is null");
            ipAddress = request.getRemoteAddr();
        }

//        Locale locale = request.getLocale();//LocaleContextHolder.getLocale();
//        String country = locale.getCountry();

        return ipAddress;
    }

    @RequestMapping(value = "/savemulti")
    public void saveMulti(@RequestParam("file") MultipartFile[] files){
        System.out.printf("save file");
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            System.out.println("fileName = " + fileName);
            try {
                InputStream inputStream = file.getInputStream();
                picasaImageSaver.saveImage(inputStream, contentType);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/saveimage")
    public ImageEntity saveImage(@RequestParam("file") MultipartFile file){
        ImageEntity imageEntity = null;
        System.out.println("save file");
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        System.out.println("fileName = " + fileName);
        try {
            InputStream inputStream = file.getInputStream();
            imageEntity = picasaImageSaver.saveImage(inputStream, contentType);
            imageEntity.setName(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageEntity;
    }

    private ImageEntity saveTest() throws InterruptedException {
        Thread.sleep(2000);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId("555696994848");
        return imageEntity;
    }

    @RequestMapping(value = "/albums", method = RequestMethod.GET)
    public List<String> getAlbums(){
        List<String> albumsTitles = picasaImageSaver.getAlbums();
        System.out.println("albumsTitles = " + albumsTitles);
        return albumsTitles;
    }

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public void getPhotos(){
        picasaImageSaver.getImages();
    }


    @RequestMapping(value = "/deleteimage/{photoid}", method = RequestMethod.GET)
    public void getPhotos(@PathVariable("photoid") String photoId){
        picasaImageSaver.deleteImage(photoId);
    }

    @RequestMapping(value = "/getimage/{photoid}", method = RequestMethod.GET)
    public void getImage(@PathVariable("photoid") String photoId){
        picasaImageSaver.getImage(photoId);
    }
}
