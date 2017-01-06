package edu.bupt.http;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by shixu on 2016/11/25.
 */
public class HttpRequest {
    public final static String FORMAT_XML = "xml";
    public final static String FORMAT_JSON = "json";
    public final static String FORMAT_PLAIN = "plain";


    public final static String PATTERN_NER = "ner";
    public final static String PATTERN_POS = "pos";
    public final static String PATTERN_SRL = "srl";
    public final static String PATTERN_DP = "dp";


    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String sendGet(String URL, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String URLNameString = URL + "?" + param;
          //  System.out.println("url:" + URLNameString);
            URL realURL = new URL(URLNameString);
            // 打开和URL之间的连接
            URLConnection connection = realURL.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                // System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    private String text = "人民网北京1月3日电（赵英梓）对外经济贸易大学近日发布的《2016年毕业生就业创业质量报告》显示，在就业的毕业生中，65";
    //这里是讯飞云的api  更稳定一些
    public final static String URL = "http://ltpapi.voicecloud.cn/analysis/";
    public final static String API_KEY = "E1b4c8u0f4z8H866Q7J2LjtsiCVdkzWLVzqxDiOl";


    //下面是哈工大的api
    //public final static String URL = "http://api.ltp-cloud.com/analysis/";
    //public final static String API_KEY = "l7B4n6V1EeSiJpPlyNaDQyauFTYhJO8wMsRZfAPP";



    @Test
    public void testMain() {
         //testDp();  //句法分析
       testNer();  //命名实体识别
//        testPos();  //标注
        //testSrl();  //语义角色标注
    }

    @Test
    public void testDp() {
        String pattern = "dp";
        String format = "json";
        String param = "api_key=" + API_KEY + "&text=" + text + "&pattern=" + pattern + "&format=" + format;
        System.out.println("依存句法分析：" + sendPost(URL, param));
    }

    @Test
    public void testPos() {
        String pattern = "pos";
        String format = "json";
        String param = "api_key=" + API_KEY + "&text=" + text + "&pattern=" + pattern + "&format=" + format;
        System.out.println("词性标注：" + sendGet(URL, param));
    }


    @Test
    public void testNer() {
        String pattern = "ner";
        String format = "plain";
        String param = "api_key=" + API_KEY + "&text=" + text + "&pattern=" + pattern + "&format=" + format;
        System.out.println("命名实体识别：" + sendGet(URL, param));
    }

    @Test
    public void testSrl() {
        String pattern = "srl";
        String format = "json";
        String param = "api_key=" + API_KEY + "&text=" + text + "&pattern=" + pattern + "&format=" + format;
        System.out.println("语义角色标注：" + sendGet(URL, param));
    }




    /**
     * 调用讯飞的api 返回响应结果
     *
     * @param str     要解析的内容
     * @param pattern 解析模式
     * @param format  响应格式
     * @return 响应内容
     */
    public static String xfyunHttpApi(String str, String pattern, String format) {
        String param = "api_key=" + API_KEY + "&text=" + str + "&pattern=" + pattern + "&format=" + format;
        return sendPost(URL, param);
    }
}
