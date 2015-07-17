package ua.in.petybay.EventListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ua.in.petybay.Events.OnAddAdvertCompleteEvent;
import ua.in.petybay.configuration.GeneralConfigs;
import ua.in.petybay.entity.Pet;
import ua.in.petybay.service.IAdvertService;

import java.util.UUID;

/**
 * Created by slavik on 18.07.15.
 */
@Component
public class AddAdvertListener implements ApplicationListener<OnAddAdvertCompleteEvent> {
    @Autowired
    private IAdvertService advertService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnAddAdvertCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnAddAdvertCompleteEvent event) {
        Pet advert = event.getAdvert();
        String token = UUID.randomUUID().toString();
        advertService.createVerificationToken(advert, token);

        String recipientAddress = advert.getUser().getEmail();
        String subject = "Activate your advertisement";
        String confirmationUrl = event.getAppUrl() + "/activateAdvertisement?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(GeneralConfigs.HOST + confirmationUrl);

        mailSender.send(email);
    }
}
