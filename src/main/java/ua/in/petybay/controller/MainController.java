package ua.in.petybay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import ua.in.petybay.dao.CategoryRepository;
import ua.in.petybay.entity.Advert;
import ua.in.petybay.entity.Category;
import ua.in.petybay.entity.ImageEntity;
import ua.in.petybay.entity.User;
import ua.in.petybay.security.SecUserDetails;
import ua.in.petybay.service.IAdvertService;
import ua.in.petybay.service.image.picasa.PicasaImageSaver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by slavik on 31.03.15.
 */
@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IAdvertService advertService;

    @Autowired
    private PicasaImageSaver picasaImageSaver;

    @Autowired
    ApplicationEventPublisher eventPublisher;


    @RequestMapping(value = "/advert", method = RequestMethod.POST, produces = "text/plain")
    public String saveAdvert(@Valid @RequestBody Advert advert, BindingResult bindingResult, WebRequest request,
                          Authentication authentication, HttpServletRequest httpServletRequest) throws Exception{

        if (bindingResult.hasErrors()) {
            for (ObjectError objectError: bindingResult.getAllErrors()){
                System.out.println("objectError = " + objectError);
            }
            throw new Exception("invalid request params");
        }

        System.out.println("advert : " + advert);

        boolean isUserAuthenticated = (authentication != null && authentication.isAuthenticated());

        if (isUserAuthenticated){
            advert.setState(Advert.STATE.ACTIVE);
        } else
        {
            advert.setState(Advert.STATE.WAITING);
        }

        advert.calculateAndSetPublicationDate();

        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        System.out.println("ipAddress is " + ipAddress);

        advert.setIpAddress(ipAddress);

        List<Category> categories = advertService.addCategoriesToNewAdvert(advert);
        advertService.save(advert);
        for (Category category : categories){
            if (isUserAuthenticated) {
                category.setCountActive(category.getCountActive() + 1);
            } else {
                category.setCountWaiting(category.getCountWaiting() + 1);
            }
            categoryRepository.save(category);
        }

        System.out.println("saveAdvert() authentication = " + authentication);

        if (!isUserAuthenticated) {
            System.out.println("saveAdvert() send email to confirm");
            String appUrl = request.getContextPath();
            System.out.println("saveAdvert() before publish event time = " + System.currentTimeMillis());
//            eventPublisher.publishEvent(new OnAddAdvertCompleteEvent(advert, appUrl));
            System.out.println("saveAdvert() after publish event time = " + System.currentTimeMillis());
        }

        return "all is ok";
    }


    /* get user adverts by state. The path must be like /user/adverts/{adState} but because of error
      which appears in angularJS when $http(request) called, was decided to left /text/{adState}. In
     future investigation of this problem must be done and fixed.
     I found out the problem. Such behaviour was because of installed AdBlock plugin. It filters all requests
     which have 'ad' keyword or similar.
    */
    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/user/adverts/{adState}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getUserAds(@PathVariable("adState") String adState,
                                @AuthenticationPrincipal SecUserDetails secUserDetails){

        User user = secUserDetails.getUser();
        System.out.println("getUserInfo() user = " + user);

        System.out.println("getUserAds()");


        Advert.STATE state = Advert.STATE.ACTIVE;

        switch (adState){
            case "ACTIVE" : state = Advert.STATE.ACTIVE; break;
            case "WAITING" : state = Advert.STATE.WAITING; break;
            case "NONACTIVE" : state = Advert.STATE.NONACTIVE; break;
            default: state = Advert.STATE.ACTIVE; break;
        }

        List<Advert> adverts = advertService.findByUserEmailAndState(user.getEmail(), state);
        return adverts;
    }


    @RequestMapping(value = "/advert/{advertId}", method = RequestMethod.GET, produces = "application/json")
    public Advert getAdvert(@PathVariable("advertId") String advertId){
        System.out.println("getAdvert() advertId = " + advertId);
        Advert advert = null;
        if(advertId != null && !"".equals(advertId)){
            advert = advertService.findOne(advertId);
            advert.setViewCount(advert.getViewCount() + 1);
            advertService.save(advert);
        }
        return advert;
    }

    @RequestMapping(value = "/advert/category/{category}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdvertsByCategory(@PathVariable("category") String category){
        System.out.println("getAdvertByCategory() category = " + category);
        List<Advert> adverts = null;
        if(category != null && !"".equals(category)){
            adverts = advertService.findByCategoryName(category);
            adverts = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE);
        }
        return adverts;
    }

    @RequestMapping(value = "/advert/category/{category}/page/{page}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdsByCategoryByPage(@PathVariable("category") String category,
                                             @PathVariable("page") int page){
        System.out.println("getAdvertsByCategoryByPage() category = " + category + " ; page = " + page);
//        Page<Advert> advertsPage = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE, new PageRequest(page - 1, 5));
//
//        List<Advert> advertList = advertsPage.getContent();

        List<Advert> advertList = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE, new PageRequest(page - 1, 5));

        System.out.println("getAdvertsByCategoryByPage() advertList.size() = " + advertList.size());

        return advertList;
    }

    @RequestMapping(value = "/advert/category/{category}/page/{page}/itemsperpage/{itemsperpage}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdsByCategoryByPageByItemsPerPage(@PathVariable("category") String category,
                                            @PathVariable("page") int page,
                                            @PathVariable("itemsperpage") int itemsPerPage){
        System.out.println("getAdsByCategoryByPageByItemsPerPage() category = " + category + " ; page = " + page + " ; itemsPerPage = " + itemsPerPage);
//        Page<Advert> advertsPage = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE, new PageRequest(page - 1, itemsPerPage));
//
//        List<Advert> advertList = advertsPage.getContent();

        List<Advert> advertList = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE, new PageRequest(page - 1, itemsPerPage));

        System.out.println("getAdsByCategoryByPageByItemsPerPage() advertList.size() = " + advertList.size());

        return advertList;
    }

    @RequestMapping(value = "/advert/category/{categoryName}/count", method = RequestMethod.GET)
    public Long getAdsCountByCategory(@PathVariable("categoryName") String categoryName){

        Category category = categoryRepository.findFirstByName(categoryName);
        Long adsCountByCategory  = category.getCountActive();
        System.out.println("getAdsCountByCategory() count = " + adsCountByCategory);
        return adsCountByCategory;
    }

    @RequestMapping(value = "/advert", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdverts(){
        System.out.println("getAdverts()");
        return  advertService.findAll();
    }


    @RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
    public List<Category> getCategorieByTopLevel(){
        System.out.println("getCategorieByTopLevel()");
        int TOP_LEVEL = 1;
        return  categoryRepository.findByLevel(TOP_LEVEL);
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


    @RequestMapping(value = "/saveadvert")
    public void makeTestSave (){
        System.out.println("makeTestSave() start");
        Category realEstate = new Category();
        realEstate.setName("realestate");
        realEstate.setLevel(1);

        Category flatRent = new Category();
        flatRent.setName("flatrent");

        Category flatLongTerm = new Category();
        flatLongTerm.setName("flatlongterm");


        realEstate = categoryRepository.save(realEstate);
        flatRent = categoryRepository.save(flatRent);
        flatLongTerm = categoryRepository.save(flatLongTerm);

        List<Category> childs = new ArrayList<>();
        childs.add(flatRent);

        realEstate.setChilds(childs);
        categoryRepository.save(realEstate);

        childs.clear();
        childs.add(flatLongTerm);

//        flatRent.setParent(realEstate);
        flatRent.setChilds(childs);
        categoryRepository.save(flatRent);

//        flatLongTerm.setParent(flatRent);
        categoryRepository.save(flatLongTerm);


        System.out.println("makeTestSave() finish ");
    }
}
