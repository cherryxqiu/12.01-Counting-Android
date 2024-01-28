package com.example.myapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import android.content.res.AssetManager;
import java.io.BufferedReader;

public class CheckWord {
    private Set<String> commonWords;

    public CheckWord(AssetManager assetManager) throws IOException {
        commonWords = new HashSet<>();
        InputStream inputStream = assetManager.open("commonWords.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            commonWords.add(line.toLowerCase());
        }
    }

    public boolean isCommon(String str){
        return commonWords.contains(str);
    }
}