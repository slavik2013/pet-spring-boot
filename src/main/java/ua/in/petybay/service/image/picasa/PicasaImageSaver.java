package ua.in.petybay.service.image.picasa;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.in.petybay.entity.ImageEntity;
import ua.in.petybay.service.image.ImageSaver;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by slavik on 22.04.15.
 */
@Service
public class PicasaImageSaver implements ImageSaver {


//    @Autowired
//    private GoogleCredential googleCredential ;
//
//    @Autowired
//    private PicasawebService picasawebService;

    @Autowired
    private PicasaClient picasaClient;

    private String getAlbumId(){
        return "6005195816372655985";
    }

    @Override
    public ImageEntity saveImage(InputStream inputStream, String contentType) {
        String albumId = getAlbumId();
        ImageEntity imageEntity = null;
        try {
            PhotoEntry photoEntry = picasaClient.insertPhoto(inputStream, contentType, albumId);

            String id = photoEntry.getGphotoId();
            String htmlLink = photoEntry.getMediaContents().get(0).getUrl();
            imageEntity = new ImageEntity();
            imageEntity.setId(id);
            imageEntity.setHtmlLink(htmlLink);

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("e = " + e);
        }
        return imageEntity;
    }

    public List<String> getAlbums(){
        List<String> stringList = new ArrayList<String>();
        try {
           List<AlbumEntry> albumEntries = picasaClient.getAlbums();
            for (AlbumEntry albumEntry : albumEntries) {
                stringList.add(albumEntry.getTitle().getPlainText());
            }

        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return stringList;
    }

    public void getImages() {
        String albumId = getAlbumId();
        try {
            List<PhotoEntry> photoEntries = picasaClient.getPhotos(albumId);
            for (PhotoEntry photo : photoEntries) {
                String photoId = photo.getGphotoId();
                System.out.println("photoId = " + photoId);
                String url = photo.getMediaContents().get(0).getUrl();
                System.out.println("url = " + url);
            }
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    public void deleteImage(String photoId){
        picasaClient.deleteImage(photoId);
    }

    public void getImage(String photoId){
        picasaClient.findImage(photoId);
    }

}
