package ua.in.petybay.Events;

import org.springframework.context.ApplicationEvent;
import ua.in.petybay.entity.Advert;

/**
 * Created by slavik on 14.07.15.
 */
public class OnAddAdvertCompleteEvent extends ApplicationEvent {
    private final String appUrl;
    private final Advert advert;

    public OnAddAdvertCompleteEvent(Advert advert, String appUrl) {
        super(advert);
        this.advert = advert;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }


    public Advert getAdvert() {
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
