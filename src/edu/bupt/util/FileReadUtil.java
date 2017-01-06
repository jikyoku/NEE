package edu.bupt.util;


import edu.bupt.model.TriggerWord;
import edu.bupt.serviceimpl.SubEventServiceImpl;
import edu.bupt.serviceimpl.TriggerWordServiceImpl;
import edu.bupt.service.SubEventService;
import edu.bupt.service.TriggerWordService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixu on 2016-08-01.
 */
public class FileReadUtil {

    /**
     * 从文件中将触发词读入到数据库中去
     */
    private static void readFile(String path) {
        File file = new File(path);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        SubEventService subEventService = new SubEventServiceImpl();
        TriggerWordService twService = new TriggerWordServiceImpl();
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";

            while (!((line = bufferedReader.readLine()) == null)) {
                String regex = "，";
                System.out.println(line);
                line = line.replaceAll("\\t", "，");
                String[] words = line.split(regex);
                for (String word :
                        words) {
                    System.out.println(word);
                }

                String eventName = words[0];
                int id = subEventService.getIdByName(eventName);
                System.out.println(eventName + "-----------");
                for (int i = 1; i < words.length; i++) {
                    TriggerWord tw = new TriggerWord();
                    tw.setEventSubTypeId(id);
                    tw.setTriggerWordContent(words[i]);
                    twService.addTrgWd(tw);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fileReader != null)
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


    /**
     * 从本地文件读取词条 一行为一个词 放到list里面 方便后面处理
     *
     * @param path 文件路径
     * @return List 返回词条列表
     */
    public static List<String> readDict(String path) {
        File file = new File(path);
        File[] files;
        List<String> words = new ArrayList<>();
        if (file.isDirectory())
            files = file.listFiles();
        else {
            System.err.println("写入路径要求是目录名！");
            return words;
        }
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            if (files != null)
                for (File fileItem : files) {
                    fileReader = new FileReader(fileItem);
                    bufferedReader = new BufferedReader(fileReader);
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        words.add(line);
                        line = bufferedReader.readLine();
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (fileReader != null)
                    fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return words;
    }


    private static void readAndAdd(String path) {
        File file;
        FileReader fileReader;
        BufferedReader bufferedReader;
        TriggerWordService triggerWordService = new TriggerWordServiceImpl();
        try {
            file = new File(path);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                TriggerWord tw = new TriggerWord();
                tw.setEventSubTypeId(58);
                tw.setTriggerWordContent(line);
                triggerWordService.addTrgWd(tw);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String path = "C:\\Users\\shixu\\Desktop\\triggerLess.txt";
        FileReadUtil.readAndAdd(path);
    }
}
