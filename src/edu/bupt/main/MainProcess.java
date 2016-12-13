package edu.bupt.main;

import edu.bupt.main.preprocess.PreProcess;
import edu.bupt.model.News;

import edu.bupt.service.NewsService;
import edu.bupt.serviceimpl.NewsServiceImpl;


import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * Created by shi xu on 2016-07-25.
 *
 * @author shi xu
 */
public class MainProcess {
    private static NewsService newsService = new NewsServiceImpl();
    private static List<Integer> newsIdList = newsService.getIdList();
    private static Map<Integer, News> newsMap;

    public static void main(String[] args) throws IOException {
        int startId = 5000;
        newsMap = newsService.getNewsMap(startId,null,null);
        System.out.println("=========="+newsMap.size());
        for (Map.Entry<Integer, News> newsEntry :
                newsMap.entrySet()) {
            News newsObj = newsEntry.getValue();
            PreProcess.handler(newsObj);
        }
    }
}
