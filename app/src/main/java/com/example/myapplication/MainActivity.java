package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.top1);
        Button button2 = findViewById(R.id.top5);

        TextView text = findViewById(R.id.textView);
        TextInputEditText input = findViewById(R.id.Text);


        button1.setOnClickListener(view -> {
//            Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_LONG).show();
            try {
                text.setText(counterFor1());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        button2.setOnClickListener(view -> {
            try {
                text.setText(counter());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public String counterFor1() throws IOException {

        TextInputEditText input = findViewById(R.id.Text);

        CheckWord commonWords = new CheckWord(MainActivity.this);

        ReadWord readWord = new ReadWord(MainActivity.this, input.getText()+".txt");

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

        int[] maxFive = find5MaxIndex(counts);
        String s = "The most common word in this text file is \n\"" + words[0] + "\" with " + counts[0] + " occurrences.";
        return s;
    }

    public String counter() throws IOException {

        TextInputEditText input = findViewById(R.id.Text);

        CheckWord commonWords = new CheckWord(MainActivity.this);

        ReadWord readWord = new ReadWord(MainActivity.this, input.getText()+".txt");

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

        int[] maxFive = find5MaxIndex(counts);
        String s = "The top 5 most common words are \n \n";
        int cnt = 1;
        for (int i : maxFive) {
            s+= cnt+". \"" + words[i] + "\" with " + counts[i] + " occurrences \n";
            cnt++;
        }
        return s;
    }

    static int[] find5MaxIndex(int[] counts) {
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

    private static void addIndex(int[] index, int[] counts, int i, int ct) {
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
