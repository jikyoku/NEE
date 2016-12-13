

package test.edu.bupt.reg;

import edu.bupt.util.RegexHandler;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * RegexHandler Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>07/26/2016</pre>
 */
public class RegexHandlerTest extends TestCase {
    public RegexHandlerTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: cutSentence(String content)
     */
    public void testCutSentence() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: removeTagger(String content)
     */
    public void testRemoveTagger() throws Exception {
//TODO: Test goes here...
        String str = "/w”/w课堂/n体验/vn有/v啥/r内容/n语文/n视频/n内容/n：" +
            "/w六/m年级/n下/f学期/n课文/n《/w孔子/nr游/v春/tg》/w分析/vn课堂/n作业/vn：" +
            "/w1./m围绕/v课文/n句子/n分析/vn、/w仿/v写/v句子/n等/u2./m默写/v关于/p春风/n、" +
            "/w春雨/n、/w春/tg花/v诗句/n3./m成语/n填空/v并/c分类/vn4./m作文/n（/w题目/n为/p用" +
            "/p自身/r事例/n写/v对/p孔子/nr名/q言/vg“/w三/m人/n行/v，/w必/d有/v师/ng焉/y”/w理解/" +
            "v和/c感受/v，/w要求/v200/m字/n以上/f）/w数学/n视频/n内容/n：/w两道/m题/n目的/n讲解/v：/" +
            "w用/p不同/a长方形/n铁皮/n拼/v长方体/n，" +
            "/w求/v拼/v出/v长方体/n最/d大/a容积/n；/w用/p线段/n图/n求/v书/n总/b页/q数/m";
        str = RegexHandler.removeTagger(str,RegexHandler.REGEX_OF_POINTER,RegexHandler.REPLACE_BY_NOTHING);
        System.out.println(str);
    }

    /**
     * Method: wordCombination(String sentence)
     */
    public void testWordCombination() throws Exception {
//TODO: Test goes here...
//    String str = "昨天/t上午/t，/w天津/ns塘沽/ns校/ng区/n发生/v了/ul一场/m巨大/a的/uj爆炸/v";
        String str = "　　 摘要/n：/w高考/v分数线/n出/v炉/ng，/w高考/v填报/v志愿/n成/v" +
                "了/ul重头/n大戏/n。/w高考/v填报/v志愿/n不仅/c要/v关系/n到/v自己/" +
                "r能否/v被/p高校/j录取/v，/w更为/d重要/a的/uj是/v：/w个人/n填/v志愿" +
                "/n选择/v高校/j、/w专业/n时/ng，/w即/v内在/b的/uj确定/v了/ul自己/r未" +
                "来/t学业/n及/c职业/n的/uj发展/vn路线/n及/c发展/vn状态/n。/w<br/en/><" +
                "br/en/>高考/v分数线/n出/v炉/ng，/w高考/v填报/v志愿/n成/v了/ul重头/n大" +
                "戏/n。/w高考/v填报/v志愿/n不仅/c要/v关系/n到/v自己/r能否/v被/p高校/j录" +
                "取/v，/w更为/d重要/a的/uj是/v：/w个人/n填/v志愿/n选择/v高校/j、/w专业" +
                "/n时/ng，/w即/v内在/b的/uj确定/v了/ul自己/r未来/t学业/n及/c职业/n的/u" +
                "j发展/vn路线/n及/c发展/vn状态/n。/w<br/en/><br/en/>不少/m同学/n心仪/" +
                "v去/v一些/m经济/n发达/a、/w环境/n优美/a的/uj城市/n上/f学/v；/w也/d有/" +
                "v一些/m同学/n希望/v在/p所在/n地区/n上/f大学/n，/w也/d有/v一些/m考生/n".trim() ;

        System.out.println(RegexHandler.wordCombination(str));
        System.out.println(str);
    }

    /**
     * Method: main(String[] args)
     */
    public void testMain() throws Exception {
//TODO: Test goes here...
        List<?> listStr = new ArrayList<String>();
        List list = new ArrayList();
        list.add(listStr);
    }


    public static Test suite() {
        return new TestSuite(RegexHandlerTest.class);
    }
} 
