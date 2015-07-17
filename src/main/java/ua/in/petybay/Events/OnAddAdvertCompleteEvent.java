package ua.in.petybay.Events;

import org.springframework.context.ApplicationEvent;
import ua.in.petybay.entity.Pet;

/**
 * Created by slavik on 14.07.15.
 */
public class OnAddAdvertCompleteEvent extends ApplicationEvent {
    private final String appUrl;
    private final Pet advert;

    public OnAddAdvertCompleteEvent(Pet advert, String appUrl) {
        super(advert);
        this.advert = advert;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }


    public Pet getAdvert() {
        return advert;
    }

    @Override
    public String toString() {
        return "OnRegistrationCompleteEvent{" +
                "appUrl='" + appUrl + '\'' +
                ", advert=" + advert +
                '}';
    }
}
