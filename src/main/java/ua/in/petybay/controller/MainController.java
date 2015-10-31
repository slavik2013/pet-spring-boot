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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ua.in.petybay.dao.CategoryRepository;
import ua.in.petybay.dao.CityRepository;
import ua.in.petybay.dao.RegionRepository;
import ua.in.petybay.entity.*;
import ua.in.petybay.security.SecUserDetails;
import ua.in.petybay.service.IAdvertService;
import ua.in.petybay.service.image.picasa.PicasaImageSaver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
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

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;


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

//        if (isUserAuthenticated){
//            advert.setState(Advert.STATE.ACTIVE);
//        } else
//        {
//            advert.setState(Advert.STATE.WAITING);
//        }

        advert.setState(Advert.STATE.ACTIVE);

        advert.calculateAndSetPublicationDate();

        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        System.out.println("ipAddress is " + ipAddress);

        advert.setIpAddress(ipAddress);

        List<Category> categories = advertService.addCategoriesToNewAdvert(advert);

        Location location = advert.getLocation();
        Region region = location.getRegion();
        City city = location.getCity();

        region = regionRepository.findOneByName(region.getName());
        city = cityRepository.findOneByName(city.getName());

        location.setRegion(region);
        location.setCity(city);

        advert.setLocation(location);

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

        !!! I found out the problem. Such behaviour was because of installed AdBlock plugin. It filters all requests
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


//    @RequestMapping(value = "/advert/allcategory", method = RequestMethod.GET, produces = "application/json")
//    public List<Advert> getAdvertsAllcategory(@RequestParam("region") String regionName,
//                                              @RequestParam("city") String cityName){
//        System.out.println("getAdvertsAllcategory() ");
//        return null;
//    }

//    @RequestMapping(value = "/advert/category/{category}", method = RequestMethod.GET, produces = "application/json")
//    public List<Advert> getAdvertsByCategory(@PathVariable("category") String category){
//        System.out.println("getAdvertByCategory() category = " + category);
//        List<Advert> adverts = null;
//        if(category != null && !"".equals(category)){
//            adverts = advertService.findByCategoryName(category);
//            adverts = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE);
//        }
//        return adverts;
//    }

    @RequestMapping(value = "/advert/category/{category}/page/{page}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdsByCategoryByPage(@PathVariable("category") String category,
                                             @PathVariable("page") int page){
        System.out.println("getAdvertsByCategoryByPage() category = " + category + " ; page = " + page);


        List<Advert> advertList = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE, new PageRequest(page - 1, 40));

        System.out.println("getAdvertsByCategoryByPage() advertList.size() = " + advertList.size());

        return advertList;
    }

    @RequestMapping(value = "/advert/category/{category}/{subcategory}/page/{page}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdsByCategoryBySubcategoryByPage(@PathVariable("category") String category,
                                                            @PathVariable("subcategory") String subcategory,
                                               @PathVariable("page") int page){
        System.out.println("getAdvertsByCategoryByPage() category = " + category + " ; page = " + page);

        List<Advert> advertList = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE, new PageRequest(page - 1, 40));

        System.out.println("getAdvertsByCategoryByPage() advertList.size() = " + advertList.size());

        return advertList;
    }

    @RequestMapping(value = "/advert/category/{category}/page/{page}/itemsperpage/{itemsperpage}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdsByCategoryByPageByItemsPerPage(@PathVariable("category") String category,
                                            @PathVariable("page") int page,
                                            @PathVariable("itemsperpage") int itemsPerPage) throws Exception{

        if( itemsPerPage > 100 || itemsPerPage < 1)
            throw new Exception("itemsPerPage could not be more than 40 and less then 1");
        if ( page > 500 || page < 1)
            throw new Exception("page number could not be more than 500 and less then 1");

        System.out.println("getAdsByCategoryByPageByItemsPerPage() category = " + category + " ; page = " + page + " ; itemsPerPage = " + itemsPerPage);

        List<Advert> advertList = advertService.findByCategoryNameAndState(category, Advert.STATE.ACTIVE, new PageRequest(page - 1, itemsPerPage));

        System.out.println("getAdsByCategoryByPageByItemsPerPage() advertList.size() = " + advertList.size());

        return advertList;
    }

    @RequestMapping(value = "/advert/category/{category}/{subcategory}/page/{page}/itemsperpage/{itemsperpage}", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdsByCategoryByPageByItemsPerPage(@PathVariable("category") String category,
                                                             @PathVariable("subcategory") String subcategory,
                                                             @PathVariable("page") int page,
                                                             @PathVariable("itemsperpage") int itemsPerPage) throws Exception{

        if( itemsPerPage > 100 || itemsPerPage < 1)
            throw new Exception("itemsPerPage could not be more than 40 and less then 1");
        if ( page > 500 || page < 1)
            throw new Exception("page number could not be more than 500 and less then 1");

        System.out.println("getAdsByCategoryByPageByItemsPerPage() category = " + category + " ; page = " + page + " ; itemsPerPage = " + itemsPerPage);

        List<Advert> advertList = advertService.findByCategoryNameAndState(subcategory, Advert.STATE.ACTIVE, new PageRequest(page - 1, itemsPerPage));

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

    @RequestMapping(value = "/advert/category/{categoryName}/{subcategoryName}/count", method = RequestMethod.GET)
    public Long getAdsCountByCategoryBySubCategory(@PathVariable("categoryName") String categoryName, @PathVariable("subcategoryName") String subcategoryName){
        Category category = categoryRepository.findFirstByName(subcategoryName);
        Long adsCountByCategory  = category.getCountActive();
        System.out.println("getAdsCountByCategoryBySubCategory() count = " + adsCountByCategory);
        return adsCountByCategory;
    }


    @RequestMapping(value = "/advert", method = RequestMethod.GET, produces = "application/json")
    public List<Advert> getAdverts(){
        System.out.println("getAdverts()");
        return  advertService.findAll();
    }


    @RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
    public List<Category> getCategories(){
        System.out.println("getCategorieByTopLevel()");

//        return  categoryRepository.findByLevel(Category.TOP_LEVEL);
        return categoryRepository.findAll();
    }

    @RequestMapping(value = "/categorywrapper", method = RequestMethod.GET, produces = "application/xml")
    public CategoriesWrapper getCategoryWrapper(){
        System.out.println("getCategoryWrapper()");

        List<Category> categories = categoryRepository.findByLevel(Category.TOP_LEVEL);
        CategoriesWrapper categoriesWrapper = new CategoriesWrapper();
        categoriesWrapper.setList(categories);
        return categoriesWrapper;
    }

    @RequestMapping(value = "/topcategory", method = RequestMethod.GET, produces = "application/json")
    public List<Category> getCategoriesByTopLevel(){
        System.out.println("getCategorieByTopLevel()");
        return  categoryRepository.findByLevel(Category.TOP_LEVEL);
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

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public void addCategory(@RequestBody Category category){

        int count = (int)((long)(categoryRepository.countByLevel(Category.TOP_LEVEL)));
        if (category.getDisplayOrder() == 0){
            category.setDisplayOrder(count + 1);
        }

        categoryRepository.save(category);

        if (category.getLevel() != Category.TOP_LEVEL && category.getParent() != null) {
            Category parentCategory = categoryRepository.findFirstByName(category.getParent());
            List<Category> childs = parentCategory.getChilds();
            if (childs == null)
                childs = new ArrayList();
            childs.add(category);
            parentCategory.setChilds(childs);
            categoryRepository.save(parentCategory);
        }

        System.out.println("addCategory() category = " + category);
    }

    @RequestMapping(value = "/region", method = RequestMethod.GET)
    public List<Region> findAllRegions(){
        System.out.println("findAllRegions()");
        List<Region> regions =  regionRepository.findAll();
        for (Region region : regions){
            region.setCities(null);
        }
        return regions;
    }

    @RequestMapping(value = "/region/{name}", method = RequestMethod.GET)
    public Region findRegionByName(@PathVariable("name") String name){
        System.out.println("findRegionByName() name = " + name);
        Region region = regionRepository.findOneByName(name);
        System.out.println("findRegionByName() region = " + region);
        return region;
    }

    @RequestMapping(value = "/cities/region/{name}", method = RequestMethod.GET)
    public List<City> findCitiesByRegionByName(@PathVariable("name") String name){
        System.out.println("findCitiesByRegionByName() name = " + name);

        List<City> cities = cityRepository.findByRegionName(name);
        System.out.println("findCitiesByRegionByName() cities.size = " + cities.size());
        return cities;
    }

    @RequestMapping(value = "/advert/region/{regionName}", method = RequestMethod.GET)
    public List<Advert> findAdvertsByRegion(@PathVariable("regionName") String regionName,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "itemsPerPage", defaultValue = "40") int itemsPerPage) throws Exception{

        if( itemsPerPage > 100 || itemsPerPage < 1)
            throw new Exception("itemsPerPage could not be more than 40 and less then 1");
        if ( page > 500 || page < 1)
            throw new Exception("page number could not be more than 500 and less then 1");

       return  advertService.findByRegionNameAndState(regionName, Advert.STATE.ACTIVE, new PageRequest(page - 1, itemsPerPage));
    }

    @RequestMapping(value = "/advert/city/{cityName}", method = RequestMethod.GET)
    public List<Advert> findAdvertsByCity(@PathVariable("cityName") String cityName,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "itemsPerPage", defaultValue = "40") int itemsPerPage) throws Exception {

        if( itemsPerPage > 100 || itemsPerPage < 1)
            throw new Exception("itemsPerPage could not be more than 40 and less then 1");
        if ( page > 500 || page < 1)
            throw new Exception("page number could not be more than 500 and less then 1");

        return  advertService.findByCityNameAndState(cityName, Advert.STATE.ACTIVE, new PageRequest(page - 1, itemsPerPage));
    }

    @RequestMapping(value = "/advert/region/{regionName}/city/{cityName}", method = RequestMethod.GET)
    public List<Advert> findAdvertsByRegionByCity(@PathVariable("regionName") String regionName,
                                                  @PathVariable("cityName") String cityName,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "itemsPerPage", defaultValue = "40") int itemsPerPage) throws Exception {

        if( itemsPerPage > 100 || itemsPerPage < 1)
            throw new Exception("itemsPerPage could not be more than 40 and less then 1");
        if ( page > 500 || page < 1)
            throw new Exception("page number could not be more than 500 and less then 1");

        return  advertService.findByRegionNameAndCityNameAndState(regionName, cityName, Advert.STATE.ACTIVE, new PageRequest(page - 1, itemsPerPage));
    }

//    @RequestMapping(value = "/generateRegions", method = RequestMethod.GET)
    public String generateRegions() throws Exception{
        File fXmlFile = new File("ua-cities-google.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("region");

        List<Region> regions = new LinkedList<Region>();

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            NodeList childs = nNode.getChildNodes();

            Element eElementRegion = (Element) nNode;
            String regionNameRu = eElementRegion.getAttribute("name");
            String regionNameUa = eElementRegion.getAttribute("nameUA");
            String regionNameEn = eElementRegion.getAttribute("nameEN");

            Title regiontitleRu = new Title();
            regiontitleRu.setLanguage("ru");
            regiontitleRu.setTitle(regionNameRu);

            Title regiontitleUa = new Title();
            regiontitleUa.setLanguage("ua");
            regiontitleUa.setTitle(regionNameUa);

            Title regiontitleEn = new Title();
            regiontitleEn.setLanguage("en");
            regiontitleEn.setTitle(regionNameEn);

            List<Title> regiontitles = new LinkedList<>();
            regiontitles.add(regiontitleEn);
            regiontitles.add(regiontitleRu);
            regiontitles.add(regiontitleUa);



            List<City> cities = new LinkedList<City>();
            List<String> citiesNames = new ArrayList<>();

            Region region = new Region();
            region.setName(regionNameEn.replaceAll("\\s+", ""));
            region.setTitles(regiontitles);


            for (int j = 0; j < childs.getLength(); j++) {
                Node nNode2 = childs.item(j);
                if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

                    City city = new City();
                    city.setRegionName(regionNameEn.replaceAll("\\s+", ""));

                    Element eElement = (Element) nNode2;
                    String cityNameRu = eElement.getAttribute("name");
                    String cityNameUa = eElement.getAttribute("nameUA");
                    String cityNameEn = eElement.getAttribute("nameEN");

                    String lat = eElement.getAttribute("lat");
                    String lon = eElement.getAttribute("lon");

                    Title titleRu = new Title();
                    titleRu.setLanguage("ru");
                    titleRu.setTitle(cityNameRu);

                    Title titleUa = new Title();
                    titleUa.setLanguage("ua");
                    titleUa.setTitle(cityNameUa);

                    Title titleEn = new Title();
                    titleEn.setLanguage("en");
                    titleEn.setTitle(cityNameEn);

                    List<Title> titles = new LinkedList<>();
                    titles.add(titleEn);
                    titles.add(titleRu);
                    titles.add(titleUa);

                    String cityName = cityNameEn.replaceAll("\\s+", "");
                    city.setName(cityName);
                    city.setTitles(titles);
                    city.setLat(Double.parseDouble(lat));
                    city.setLon(Double.parseDouble(lon));

                    cities.add(city);

                    citiesNames.add(city.getName());
                }
            }

            for (City city : cities){
                cityRepository.save(city);
            }


//            region.setCities(cities);
//            region.setCitiesNames(citiesNames);

            regions.add(region);
        }

        for (Region region : regions){
            regionRepository.save(region);
        }

        return "regions generated";
    }
}
