package com.wagdynavas.sushi2go.util.files;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImagesUtil {


    private static final String resourceFolder = "src/main/resources/static/img/menu";
    private static final String fileSeparator = "/";

    public static Path saveImage(MultipartFile file) throws Exception {
        return saveImage(file.getBytes(), file.getOriginalFilename());
    }

    public static Path saveImage(byte[] bytes, String fileName) throws Exception {
        Path sourceFolderPath = Paths.get(resourceFolder );
        String absolutePath = sourceFolderPath.toFile().getAbsolutePath();
        Path imagePath = Paths.get(absolutePath + fileSeparator +  fileName);


        Files.write(imagePath, bytes);

        return imagePath;
    }


    public static Path saveImageInNewDirectory(byte[] bytes, String fileName, String newDirectory) throws Exception {
        if (newDirectory.isEmpty()) {
            return saveImage(bytes, fileName);
        }

        Path sourceFolderPath = Paths.get(resourceFolder + fileSeparator + newDirectory + fileSeparator );
        if (Files.isDirectory(sourceFolderPath)) {
            return saveImage(bytes, fileName);
        } else {
            sourceFolderPath = Files.createDirectory(sourceFolderPath);
            String absolutePath = sourceFolderPath.toFile().getAbsolutePath();
            Path imagePath = Paths.get(absolutePath + fileSeparator +  fileName);

            Files.write(imagePath, bytes);
            return imagePath;
        }
    }


    public static String changeImageFileName(String newName, String oldName) {
        newName = newName.replaceAll(" ", "-").toLowerCase();
        int dotIndex = oldName.indexOf(".");
        String imageExtension = oldName.substring(dotIndex);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return newName + LocalDateTime.now().format(formatter) + imageExtension;
    }

}
