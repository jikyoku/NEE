package edu.bupt.util;


import edu.bupt.main.segment.SegmentHandler;
import edu.bupt.mapper.SentenceTempMapper;
import edu.bupt.mapper.TFIDFWordMapper;
import edu.bupt.mapperimpl.SentenceTempMapperImpl;
import edu.bupt.mapperimpl.TFIDFWordMapperImpl;
import edu.bupt.service.NewsService;
import edu.bupt.serviceimpl.NewsServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class SentenceUtil {
    private static final String END_LABLE = "。|！|\\.|？|\\?";

    private static NewsService newsService = new NewsServiceImpl();
    private static SentenceTempMapper sentenceTempMapper = new SentenceTempMapperImpl();
    private static TFIDFWordMapper tfIdfMapper = new TFIDFWordMapperImpl();




    private String arrayToString(String[] strs) {
        StringBuilder sb = new StringBuilder();
        for (String str :
                strs) {
            sb.append(str);
        }
        return sb.toString();
    }



    private static List<String> newsSeparate(String news, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return Arrays.asList(pattern.split(news));
    }
}
