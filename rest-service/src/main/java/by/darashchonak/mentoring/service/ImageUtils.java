package by.darashchonak.mentoring.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;

public class ImageUtils {

    public static String encodeImage(String pathToFile) throws IOException {

        Path path = Paths.get(pathToFile);

        File file = path.toFile();
        FileInputStream imageInFile = new FileInputStream(file);
        byte imageData[] = new byte[(int) file.length()];
        imageInFile.read(imageData);

        imageInFile.close();

        return Base64.encodeBase64URLSafeString(imageData);
    }

    public static void decodeImage(String imageDataString) throws IOException {
        byte[] imageByteArray = Base64.decodeBase64(imageDataString);

        FileOutputStream imageOutFile = new FileOutputStream(
                "/Users/jeeva/Pictures/wallpapers/water-drop-after-convert.jpg");

        imageOutFile.write(imageByteArray);

        imageOutFile.close();
    }
}