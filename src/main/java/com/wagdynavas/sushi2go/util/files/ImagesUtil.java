package com.wagdynavas.sushi2go.util.files;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImagesUtil {


    private static final String resourceFolder = "media";
    private static final String fileSeparator = "/";

    public static Path saveImage(MultipartFile file) throws Exception {
        return saveImage(file.getBytes(), file.getOriginalFilename());
    }

    public static Path saveImage(byte[] bytes, String fileName) throws Exception {
       return saveImage(bytes, fileName, null);
    }

    public static Path saveImage(byte[] bytes, String fileName, Path directory) throws Exception {
        String absolutePath = directory.toFile().getAbsolutePath();
        Path imagePath = Paths.get(absolutePath + fileSeparator +  fileName);


        Files.write(imagePath, bytes);

        return imagePath;
    }


    public static Path saveImageInNewDirectory(byte[] bytes, String fileName, String newDirectory) throws Exception {

        if (newDirectory != null) {
            Path sourceFolderPath = Paths.get(resourceFolder + fileSeparator + newDirectory + fileSeparator );
            if (!Files.isDirectory(sourceFolderPath)) {
                sourceFolderPath = Files.createDirectory(sourceFolderPath);
            }
            return saveImage(bytes, fileName, sourceFolderPath);
        }
        return null;
    }


    public static String changeImageFileName(String newName, String oldName) {
        newName = newName.replaceAll(" ", "-").toLowerCase();
        int dotIndex = oldName.indexOf(".");
        String imageExtension = oldName.substring(dotIndex);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return newName + LocalDateTime.now().format(formatter) + imageExtension;
    }

}
