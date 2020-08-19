package com.road.letter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Service
public class FileServiceImpl implements FileService {
    public static String selectedFile = "";

    @Value("${location}")
    String fileLocation;

    @Override
    public void storeFile(MultipartFile file) throws IOException {
        try {
            File convertFile = new File(fileLocation + "/" + file.getOriginalFilename());
            if (!convertFile.getParentFile().exists()) {
                convertFile.getParentFile().mkdir();
            }
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();
        } catch (IOException e) {
            throw e;
        }

    }

    @Override
    public String[] listFiles() {
        File file = new File(fileLocation);
        if (file.exists()) {
            return file.list();
        }
        return new String[0];
    }

    @Override
    public String selectFile(String fileName) {
        if (Arrays.asList(listFiles()).contains(fileName)) {
            selectedFile = fileName;
            return "Successfully selected";
        } else {
            return "Fail selecting";
        }
    }
}
