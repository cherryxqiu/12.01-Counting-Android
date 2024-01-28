package com.example.myapplication;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Counter {
    private String word = "";

    private SpannableString ss = SpannableString.valueOf("");
    private String toPrint = "";
    private String countString = "";
    public Counter(AssetManager assetManager, String filename, String result) throws IOException {
        CheckWord commonWords = new CheckWord(assetManager);

        ReadWord readWord = new ReadWord(assetManager, filename);

        ParallelArrays parallelArrays = new ParallelArrays();

        ArrayList<String> toRead = readWord.getWords();

        for (String word: toRead) {
            if (commonWords.isCommon(word)) {
                continue;
            }
            parallelArrays.add(word);
        }

        int[] counts = parallelArrays.getCnt();
        String[] words = parallelArrays.getWords();

        int max = findMaxIndex(counts);
        int[] maxFive = find5MaxIndex(counts);
        if(result.equals("Top 5")){
            int count = 0;
            for (int i : maxFive) {
                count = count+1;
                toPrint = toPrint + count + ". \"" + words[i] +"\" with " + counts[i] + " occurrences\n";
            }
            ss = new SpannableString(toPrint);
            int textColor = Color.parseColor("#758ECB");
            for(int i: maxFive) {
                ss.setSpan(new ForegroundColorSpan(textColor), toPrint.indexOf(words[i]), toPrint.indexOf(words[i]) + words[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }



        }
        else if(result.equals("Common Word")){
            word = words[max];
            countString = Integer.toString(counts[max]);

        }


    }
    public String getWord(){
        return word;
    }
    public String getCountString(){return countString;}
    public SpannableString getToPrint(){return ss;}

    private int findMaxIndex(int[] counts) {
        int i;
        int max = counts[0];
        for (i = 1; i < counts.length; i++) {
            if (counts[i] > max)
                max = counts[i];

        }
        for (int j = 0; j < counts.length; j++) {
            if (counts[j] == max) {
                return j;
            }
        }
        return max;

    }
    private int[] find5MaxIndex(int[] counts) {
        int[] index = new int[5];
        int[] max = new int[5];
        int filled = 0;
        for (int i = 0; i < counts.length; i++) {
            if (max[filled] < counts[i]) {
                addIndex(index, max, i, counts[i]);
                if (filled != 4) {
                    filled++;
                }
            }
        }
        return index;
    }

    private void addIndex(int[] index, int[] counts, int i, int ct) {
        int indexToAdd = 0;
        for (int j = 4; j >= 0; j--) {
            if (counts[j] == 0) {
                continue;
            }
            if (counts[j] > ct) {
                indexToAdd = j+1;
                break;
            }
        }

        for (int j = 3; j >= indexToAdd; j--) {
            counts[j + 1] = counts[j];
            index[j + 1] = index[j];
        }
        counts[indexToAdd] = ct;
        index[indexToAdd] = i;
    }
}