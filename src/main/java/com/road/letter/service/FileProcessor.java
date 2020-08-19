package com.road.letter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface FileProcessor {
    HashMap<String, HashSet<Integer>> getSubjects() throws IOException;
    HashMap<String, ArrayList<String[]>> getLetters(HashMap<String, Object> filterObj) throws IOException;
}
