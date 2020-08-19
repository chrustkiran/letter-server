package com.road.letter.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void storeFile(MultipartFile file) throws IOException;
    String[] listFiles();
    String selectFile(String fileName);
}
