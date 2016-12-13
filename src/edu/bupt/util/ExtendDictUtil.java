package edu.bupt.util;

import org.ansj.library.UserDefineLibrary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixu on 2016-07-26.
 */
public class ExtendDictUtil {
    public static List<String> readWordsFromFiles(String dir) throws IOException {
        File file = new File(dir);
        InputStream is = null;
        List<String> words = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File singleFile :
                    files) {
                is = new FileInputStream(singleFile);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String word = null;
                while (!(word = bufferedReader.readLine()).isEmpty()) {
                    words.add(word);
                }
            }
        }
        return words;
    }
    public static void insertWord(List<String> words){
        for (String word:
             words) {
            UserDefineLibrary.insertWord(word, "n", 1000);

        }
    }
    public static void run(){
        String dirPath = "";
        try {
            ExtendDictUtil.readWordsFromFiles(dirPath);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
