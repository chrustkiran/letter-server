package com.road.letter.controller;

import com.road.letter.constants.ContentType;
import com.road.letter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        try {
           if (ContentType.excelType.equals(file.getContentType())) {
               fileService.storeFile(file);
               return "Successfully uploaded " + file.getOriginalFilename();
           } else {
               throw new Exception("Please upload xlsx files");
           }
        } catch (IOException e) {
            throw e;
        }
    }

    @GetMapping("/list-files")
    public String[] getFiles() {
        return fileService.listFiles();
    }

    @GetMapping("/select-file")
    public String selectFile(@RequestParam("file") String fileName) {
        return fileService.selectFile(fileName);
    }
}
