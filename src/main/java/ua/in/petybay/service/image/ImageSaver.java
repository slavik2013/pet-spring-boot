package ua.in.petybay.service.image;

import com.google.gdata.data.photos.PhotoEntry;
import ua.in.petybay.entity.ImageEntity;

import java.io.InputStream;

/**
 * Created by slavik on 19.04.15.
 */
public interface ImageSaver {
    ImageEntity saveImage(InputStream inputStream, String contentType);
}
