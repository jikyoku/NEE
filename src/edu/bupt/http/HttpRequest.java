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

    private String text = "朴实的话语，诚挚的道歉，深深的鞠躬。6月29日上午，在安庆师范大学毕业典礼暨学位授予仪式上，校长闵永新的这一“意外之举”，让在场的学生和老师深受感动。<br/><br/>　　“母校是学生的家，我把学生都当作自己的孩子，道歉是自己的真情流露。”闵永新说，今后将会不断推进和完善学校建设，“在明年的毕业典礼上，我希望不用再道歉了。”<br/><br/>　　在毕业典礼上，当毕业生看着校长走上台，准备为他们上大学的“最后一课”时，大多数人以为校长会说一大段“官话”，然而他们听到的却是，校长将大学里同学曾经“吐槽”的问题统统道了出来。<br/><br/>　　“图书馆的座位少，一座难求；菱湖校区的住宿差，过于拥挤；双龙湖畔的小道泥泞，雨天难行；食堂的饭菜凉，不那么可口。”这些学生在网上经常吐槽的问题，校长闵永新都在致辞中一一承认。<br/><br/>　　校长致辞并不是仅仅说出学生关心的问题。其间，他停下致辞，走到台前，面向在场的毕业生，弯下腰，深深鞠了一躬。闵永新表示，“作为校长，要向同学们真诚地表达歉意。”<br/><br/>　　校长的真诚之举打动了在场的每一个人，全场掌声响起。“第一次看到校长向学生道歉，作为学生很受感动。”毕业生王玲在那一刻很感动。<br/><br/>　　“校长能够鞠躬道歉，真是意外又感动。”“这是我大学毕业最值得纪念的时刻。”校长道歉的图文迅速在安庆师范大学师生的朋友圈里刷屏，随后又在社交网络上引发热议。<br/><br/>　　“希望在校长的道歉之后，学校能有改变和提高。”在点赞的同时，也有青年教师在朋友圈表达了这样的期待。<br/><br/>　　闵永新坦言，学校在基础设施和后勤保障方面的确存在一些问题，“也有学生跟我反映过这些问题，我们也在全力去完善，但是还没做到让学生满意，所以有所愧疚。”<br/><br/>　　“正是因为每一个学生都爱这所学校，把学校当成自己的家，他们才会吐槽，我作为他们的‘家长’，应该放低姿态，去接受、去改变。”闵永新说，安庆师范大学目前处于上升期，正在不断推进和完善学校各方面建设，学生提出建议说明他们深爱这所学校，作为校长应该去倾听并给予他们帮助。“让学生们饱含感情地走出校门，把大学的美好经历作为财富，永久保留。”<br/><br/>　　“道歉不是目的，最终目标是如何解决问题，”闵永新说。在接下来的一年，学校将逐一解决学生关心的问题，希望自己的道歉也能够提醒学校的职能部门增强服务意识，对学生提出的问题能迅速解决。<br/><br/>　　毕业典礼过后没两天，安庆持续暴雨，因为阳台下水管道口堵塞，该校有两间宿舍出现了水流倒灌的情况，有学生将这一情况拍成视频放到微博上调侃。<br/><br/>　　闵永新第一时间看到这一视频后，立即打电话让后勤管理处负责同志赶到现场解决问题，事后又向部门同志了解情况。原来，由于学生平时没注意，将垃圾等堵塞了下水管道。<br/><br/>　　闵永新最近正带领相关部门对学校所有宿舍进行排查，“马上就要放暑假了，如果不清除所有隐患，那就很可能再次出现宿舍进水的情况，暑期又没有学生在宿舍，后果会更严重。”<br/><br/>　　“问题不可怕，怕的是无动于衷。”闵永新表示，“只有把问题一个个都解决了，学生才会更加爱学校，才会有家的归属感与温暖。”<br/><br/>　　本着这样的理念，不久前，安庆师范大学校党委书记许继荣和校长闵永新相继找到校宣传部负责同志，要求他们编辑一份《舆情周报》，对师生们在贴吧、微博、微信等平台上的“吐槽”进行搜集整理，提供给校领导。“我们会督促解决工作中出现的问题。”许继荣表示。<br/><br/>　　“希望我们的工作重点是解决问题，而不是停留在口头上。”闵永新说。";
    //这里是讯飞云的api  更稳定一些
    public final static String URL = "http://ltpapi.voicecloud.cn/analysis/";
    public final static String API_KEY = "E1b4c8u0f4z8H866Q7J2LjtsiCVdkzWLVzqxDiOl";


    //下面是哈工大的api
    //public final static String URL = "http://api.ltp-cloud.com/analysis/";
    //public final static String API_KEY = "l7B4n6V1EeSiJpPlyNaDQyauFTYhJO8wMsRZfAPP";



    @Test
    public void testMain() {
         testDp();  //句法分析
        //testNer();  //命名实体识别
        // testPos();  //标注
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
        String format = "json";
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
