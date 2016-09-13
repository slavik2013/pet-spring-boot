package ua.in.petybay.configuration;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.photos.PicasawebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import ua.in.petybay.util.FileUtil;

import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Created by slavik on 22.04.15.
 */
@Configuration
public class PicasaConfig {
    private static final String CLIENT_ID = "824804454988-glq8c5rvf5tklaftsk8hg0pdo617iq1n.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "B_5GzA-nVzEA3AhzySwDY_oj";
    private static final String FILE_NAME = "credentials.txt";

    @Bean
    @Autowired
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public PicasawebService picasawebService(GoogleCredential googleCredential){
        PicasawebService myService = new PicasawebService("petybay-app");
        myService.setOAuth2Credentials(googleCredential);
        return myService;
    }

    @Bean
    public GoogleCredential credential() throws Exception {
//            Scanner scanner = new Scanner(new FileInputStream(FILE_NAME));
//
//            String refreshToken = scanner.nextLine();
//            String accessToken = scanner.nextLine();
//
//            GoogleCredential credential = new GoogleCredential.Builder()
//                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
//                    .setJsonFactory(new JacksonFactory()).setTransport(new NetHttpTransport()).build()
//                    .setRefreshToken(refreshToken).setAccessToken(accessToken);
//
//            credential.refreshToken();
//            String newAccessToken = credential.getAccessToken();
//            FileUtil.saveAccessToken(newAccessToken);
//            return credential;
        return null;
    }
}
