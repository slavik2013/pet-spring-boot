package ua.in.petybay.service.image.picasa;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.UserFeed;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by slavik on 20.04.15.
 */
public class GooglePicasaImageSaver {
    /** Email of the Service Account */
    private static final String SERVICE_ACCOUNT_EMAIL = "545007936943-fu8ve2fd1sngevmijnnfcquie9ghdkp0@developer.gserviceaccount.com";

    /** Path to the Service Account's Private Key file */
    private static final String SERVICE_ACCOUNT_PKCS12_FILE_PATH = "mypet_secret.p12";


    private static String CLIENT_ID = "824804454988-glq8c5rvf5tklaftsk8hg0pdo617iq1n.apps.googleusercontent.com";
    private static String CLIENT_SECRET = "B_5GzA-nVzEA3AhzySwDY_oj";

    private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";


    public static void main(String[] args) throws Exception {

//        RandomAccessFile raf = new RandomAccessFile("credentials.txt", "rw");
//        raf.readLine();
//        raf.writeChars("aaa");


//        File inputFile = new File("credentials.txt");
//        File tempFile = new File("myTempFile.txt");
//
//        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//
//
//        String currentLine = reader.readLine();
//        writer.write(currentLine + System.getProperty("line.separator"));
//
//        writer.write("aaaaa");
//
//
//        writer.close();
//        reader.close();
//        boolean successful = tempFile.renameTo(inputFile);
        PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
        myService.setOAuth2Credentials(getCredentials());

//        PicasaClient picasawebClient = new PicasaClient(myService);


        URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/petybay?kind=album");

        UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);


//        for (GphotoEntry gphotoEntry : myUserFeed.getEntries()) {
//            String id = gphotoEntry.getGphotoId();
//            System.out.println("id = " + id);
//        }
//
//
//        for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
//            System.out.println(myAlbum.getTitle().getPlainText());
//            String id = myAlbum.getGphotoId();
////            System.out.println("id = " + id);
//
//        }

//        URL feedUrl2 = new URL("https://picasaweb.google.com/data/feed/api/user/petybay");
//        AlbumEntry myAlbum = new AlbumEntry();
//
//        myAlbum.setTitle(new PlainTextConstruct("application_lbum_1"));
//        myAlbum.setDescription(new PlainTextConstruct("some text about album"));
//
//        AlbumEntry insertedEntry = myService.insert(feedUrl2, myAlbum);
//
//        System.out.println("insertedEntry = " + insertedEntry);
//
//
//                List<AlbumEntry> list = null;
//        list = picasawebClient.getAlbums("petybay");
//
//
//        AlbumEntry album = null;
//
//        if(list != null && !list.isEmpty()){
//            for (AlbumEntry albumEntry : list) {
//                System.out.println("albumEntry.name = " + albumEntry.getName());
//                if(albumEntry.getName().equals("Test")){
//                    album = albumEntry;
//
//                }
//            }
//        }else{
//
//            System.out.println("NULL");
//            throw new Exception();
//        }
//
////        if (album != null) {
////            System.out.println("link = " + album.getGphotoId());
////            URL albumPostUrl = new URL("https://picasaweb.google.com/data/feed/api/user/petybay/albumid/" + album.getGphotoId());
////            myService.insert(albumPostUrl, myPhoto);
////        }
//
//        List<PhotoEntry> photos = null;
//
//
//        photos = picasawebClient.getPhotos(album);
//
//        for (PhotoEntry photo : photos) {
//            List<MediaThumbnail> mediaThumbnail = photo.getMediaThumbnails();
//            for (MediaThumbnail thumbnail : mediaThumbnail) {
////                System.out.println("thumbnail.getUrl() = " + thumbnail.getUrl());
//            }
//            List<com.google.gdata.data.media.mediarss.MediaContent> mediaContent = photo.getMediaContents();
//            for (com.google.gdata.data.media.mediarss.MediaContent content : mediaContent) {
////                System.out.println("content = " + content.getUrl());
//            }
////            System.out.println("photo = " + mediaContent.get(0).getUrl());
//        }


    }


    public static GoogleCredential getCredentials() throws Exception {
//        HttpTransport httpTransport = new NetHttpTransport();
//        JacksonFactory jsonFactory = new JacksonFactory();
//        GoogleCredential credential = new GoogleCredential.Builder()
//                .setTransport(httpTransport)
//                .setJsonFactory(jsonFactory)
//                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
//                .setServiceAccountScopes(Arrays.asList("https://picasaweb.google.com/data/"))
//                .setServiceAccountPrivateKeyFromP12File(
//                        new java.io.File(SERVICE_ACCOUNT_PKCS12_FILE_PATH))
//                .build();
//        credential.refreshToken();
//        return credential;

        GoogleCredential credential = null;
        Scanner scanner = new Scanner(new FileInputStream("credentials.txt"));

        String refreshToken = null;
        String accessToken = null;
        if(scanner.hasNext()){
            refreshToken = scanner.nextLine();
        }
        if(scanner.hasNext()){
            accessToken = scanner.nextLine();
        }

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        if(refreshToken == null || accessToken == null){


            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList("https://picasaweb.google.com/data/"))
                    .setAccessType("offline")
                    .setApprovalPrompt("force").build();

            String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
            System.out.println("Please open the following URL in your browser then type the authorization code:");
            System.out.println("  " + url);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String code = br.readLine();

            GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
//         credential = new GoogleCredential().setFromTokenResponse(response);

            refreshToken = response.getRefreshToken();
            accessToken = response.getAccessToken();

            System.out.println("refreshToken = " + refreshToken + " , accessToken = " + accessToken);
        }
        else{
            credential = new GoogleCredential.Builder()
                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
                    .setJsonFactory(jsonFactory).setTransport(httpTransport).build()
                    .setRefreshToken(refreshToken).setAccessToken(accessToken);

            Long time = credential.getExpiresInSeconds();
            System.out.println("time = " + time);

//            credential.setExpiresInSeconds(4000l);
            credential.refreshToken();
            String newAccessToken =  credential.getAccessToken();
            System.out.println("newAccessToken = " + newAccessToken);
            time = credential.getExpiresInSeconds();
            System.out.println("time = " + time);


        }
        scanner.close();

        File tempFile = new File("credentials.txt");

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


        return credential;
    }

}
