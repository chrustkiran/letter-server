package com.road.letter.controller;

import com.road.letter.service.FileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@RestController
@CrossOrigin
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    FileProcessor fileProcessor;

    @GetMapping("/get-subjects")
    public HashMap<String, HashSet<Integer>> getSubjects() throws IOException {
        try {
            return fileProcessor.getSubjects();
        } catch (IOException e) {
            throw e;
        }
    }


    @PostMapping("/get-letters")
    public HashMap<String, ArrayList<String[]>> getLetters(@RequestBody HashMap<String, Object> filterObj) throws IOException {
        try {
            return fileProcessor.getLetters(filterObj);
        } catch (IOException e) {
            throw e;
        }
    }
}
