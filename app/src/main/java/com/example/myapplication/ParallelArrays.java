package com.example.android;

import java.util.Arrays;

public class ParallelArrays {
    private static String words[] = new String[0];
    private static int cnt[] = new int[0];

    private static void expand(){
        words = Arrays.copyOf(words, words.length+1);
        cnt = Arrays.copyOf(cnt, cnt.length+1);
    }
    public static void add(String word){
        if(!contains(word)){
            expand();
            words[words.length-1] = word;
            cnt[cnt.length-1] = 1;
        }
    }

    private static boolean contains(String word){
        for (int i = 0; i < words.length; i++){
            if(words[i].equals(word)){
                cnt[i]++;
                return true;
            }
        }
        return false;
    }

    public static String[] getWords(){
        return words;
    }

    public static int[] getCnt(){
        return cnt;
    }
}