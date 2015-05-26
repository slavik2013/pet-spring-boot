package ua.in.petybay.util;

import java.io.*;

/**
 * Created by slavik on 22.04.15.
 */
public class FileUtil {
    public static void saveAccessToken(String token) throws Exception {
        File inputFile = new File("credentials.txt");
        File tempFile = new File("myTempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine = reader.readLine();
        writer.write(currentLine + System.getProperty("line.separator"));
        writer.write(token);
        writer.close();
        reader.close();
        tempFile.renameTo(inputFile);
    }
}
