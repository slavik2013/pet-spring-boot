package ua.in.petybay.service.image.picasa;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Component
public class PicasaClient {

    private static final String USER_NAME = "petybay";
    private static final String API_PREFIX = "https://picasaweb.google.com/data/feed/api/user/" + USER_NAME;
    private static final String API_PREFIX_ENTRY = "https://picasaweb.google.com/data/entry/api/user/" + USER_NAME;

    private final PicasawebService service;

    @Autowired
    public PicasaClient(PicasawebService service) {
        this.service = service;
    }

    public List<AlbumEntry> getAlbums() throws IOException, ServiceException {
        URL feedUrl = new URL(API_PREFIX + "?kind=album");
        UserFeed myUserFeed = service.getFeed(feedUrl, UserFeed.class);
        return myUserFeed.getAlbumEntries();

    }

    public List<PhotoEntry> getPhotos(AlbumEntry album) throws IOException, ServiceException {
        URL feedUrl = new URL(API_PREFIX + "/albumid/" + album.getGphotoId());
        AlbumFeed feed = service.getFeed(feedUrl, AlbumFeed.class);
        return feed.getPhotoEntries();
    }

    public List<PhotoEntry> getPhotos(String albumId) throws IOException, ServiceException {
        URL feedUrl = new URL(API_PREFIX + "/albumid/" + albumId);
        AlbumFeed feed = service.getFeed(feedUrl, AlbumFeed.class);

        return feed.getPhotoEntries();
    }

    public PhotoEntry insertPhoto(InputStream inputStream, String contentType, String albumId) throws IOException, ServiceException {
        URL feedUrl = new URL(API_PREFIX + "/albumid/" + albumId);
//        MediaFileSource myMedia = new MediaFileSource(new File("/home/liz/puppies.jpg"), "image/jpeg");
        MediaStreamSource streamSource = new MediaStreamSource(inputStream, contentType);
        PhotoEntry returnedPhoto = service.insert(feedUrl, PhotoEntry.class, streamSource);
        return returnedPhoto;
    }

    public void deleteImage(String photoId){
        try {
            URL feedUrl = new URL(API_PREFIX_ENTRY + "/albumid/6005195816372655985/photoid/" + photoId);
            service.setHeader("If-Match","*");
            service.delete(feedUrl);
            System.out.println("photo is deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findImage(String photoId){
        try {
            URL feedUrl = new URL(API_PREFIX_ENTRY + "/albumid/6005195816372655985/photoid/" + photoId);

            PhotoEntry photoEntry = service.getEntry(feedUrl, PhotoEntry.class);
            System.out.println("photo albumId = " + photoEntry.getAlbumId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}