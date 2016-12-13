package edu.bupt.util;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Created by shixu on 2016-07-25.
 *
 */
public class RegexHandler {
    public final static String REGEX_OF_WORD_TAGGER = "/[a-z]+|《+|》+|。|,|？|，|、|；|;|\\.|:|：|‘|“|'|”|/";
    public final static String REGEX_OF_HTML_TAGGER = "<\\w+\\s*/?>|\\s|●|\\s|★";
    public final static String REPLACE_BY_SPACE = " ";
    public final static String REPLACE_BY_NOTHING = "";
    public final static String REGEX_OF_SENTENCE_OFF = "。|！|？";
    public final static String REGEX_OF_POINTER = "(\\pP|\\pS)/w|/w";

    public static String[] newsSeparate(String content,String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.split(content);
    }

    /**
     * 通过正则表达式匹配 并用指定内容替换匹配到的内容
     *  @param  content
     *      需要处理的字符串
     *  @param regex
     *      正则表达式
     *  @param  replaceContent
     *      替换的内容
     * */
    public static String removeTagger(String content,String regex,String replaceContent){
        return content.replaceAll(regex,replaceContent);
    }
    @NotNull
    public static String wordCombination(String sentence){
        String regexOfChinese = "[\u0391-\uFFE5]+|\\d+";
        String regexOfTagger = "/[a-z]+";
        Pattern pattern = Pattern.compile(regexOfTagger);
        String[] words = pattern.split(sentence);
        int i = 0;
        for (String word:
             words) {
            System.out.println((i++)+":"+word);
        }

        pattern = Pattern.compile(regexOfChinese);
        String[] taggers = pattern.split(sentence);
        i = 0;
        for (String tagger:
             taggers) {
            System.out.println((i++)+":"+tagger);
        }
        System.out.println(words.length == taggers.length);

        StringBuilder stringBuilder = new StringBuilder();
        String tagger = taggers[1];
        String word = words[0];
        stringBuilder.append(word);
        for (int j = 1; j < words.length; j++) {
            word = words[j];
            if (!taggers[j+1].equals(tagger)) {
                stringBuilder.append(tagger);
                tagger = taggers[j + 1];
            }
            stringBuilder.append(word);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
//        String source = "录取的，不影响下一批学校录取。<br/><br/>未被五年制高职学校录取的，不影响其他中职学校录取。中职学校录取不分批次同时进行，但按考生志愿顺序投档录取。<br/><br/>问：今年报考中职、五年制高职院校艺术、体育类专业的考生必须参加专业考试吗？在哪里进行？<br/><br/>答：为加强中等职业学校、五年制高职院校艺术、体育类专业招生工作的管理，完善现行的艺、体考试办法，按省招考院统一制定的中职、五年制高职院校艺术、体育类考试大纲，由各州、市招生考试机构组织专业考试。专业考试成绩须与文化成绩一并录入信息管理系统。<br/><br/>填报中职、五年制高职院校艺术、体育类专业考生，都必须参加所在州、市组织的专业考试。只有参加了专业考试的考生才可以填报艺术、体育类专业。<br/><br/>问：没参加初中学生学业水平考试的往届初中毕业生怎样报读中职学校？<br/><br/>答：须到户口所在地招考机构报名、办理相关手续，获得13位数的信息登记编号，参加中职学校正常录取；也可以直接到拟就读学校报名，并参加全省统一组织的补报志愿后，由学校统一到招生考试机构办理录取手续。<br/><br/>问：未被录取的学生能否补报志愿?<br/><br/>答：能。未被录取的学生在录取工作后期，省招考院根据录取情况，组织还未完成招生计划的学校进行补录，并在网上发布补录信息。考生可上网查询录取情况，未被录取的考生关注补报志愿的时间，在补报志愿期限内到所在地招生考试机构补报志愿，参加相关学校后期补录。<br><br/>邹佳琪则/d忧心忡忡/i：/w“/w大家/r都/d说/v简单/a，/w感觉/n竞争/vn反而/d会/v更/d激烈/《dddd》";
//        System.out.println(RegexHandler.removeTagger(source,RegexHandler.REGEX_OF_HTML_TAGGER,RegexHandler.REPLACE_BY_NOTHING));
//
//        String sentence = "浙江/ns,省内/s,学校/n,田径场/nw,出现/v,新建/v,塑胶/n,跑道/n,草皮/n,环境/n,问题/n,引发/v,社会/n,关注/v,确保/v,师生/n,身心健康/l,问题/n,是/v,现有/v,塑胶/n,跑道/n,国内/s,相关/v,建设/vn,标准/n,场所/n,气味/n,感受/v,差异/n,国家/n,制订/v,标准/n,情况/n,浙江省教育厅/nw,浙江省环境保护厅/nw,浙江省/ns,城乡/n,住房/n,建设/vn,厅/n,浙江省质量技术监督局/nw,一起/s,组织/v,人员/n,研究/vn,专家/n,论证/v,国家/n,标准/n,检测/vn,指标/n,基础/n,新增/v,挥发性/n,有机化合物/nw,释放/v,率/v,甲醛/n,释放/v,率/v,指标/n,列入/v,新建/v,改造/vn,学校/n,合成/v,材料/n,运动场/n,地面/n,物质/n,限量/vd,技术/n,指标/n,检测/vn,范围/n,强化/v,合成/v,材料/n,运动/n,场地/n,建设/vn,使用/v,管理/vn,要求/v,浙江省教育厅/nw,部门/n,联合/v,印发/v,加强/v,学校/n,合成/v,材料/n,运动/n,场地/n,建设/vn,管理/vn,工作/vn,通知/n,随文/nw,公布/v,合成/v,材料/n,运动场/n,地面/n,物质/n,限量/vd,技术/n,指标/n,合成/v,材料/n,运动/n,场地/n,现场/s,气味/n,评价/v,办法/n,通知/n,强调/v,是/v,加强/v,新建/v,合成/v,材料/n,运动/n,场地/n,招标/v,管理/vn,学校/n,包括/v,学校/n,项目/n,建设/vn,单位/n,要/v,遵守/v,建设/vn,工程/n,招标/v,采购/vn,程序/n,合成/v,材料/n,运动/n,场地/n,招标/v,采购/vn,活动/vn,应/v,进入/v,政府/n,统一/vn,招标/v,投标/v,交易/n,场所/n,交易/n,保证/v,学校/n,运动/n,场地/n,建设/vn,质量/n,招标/v,文件/n,应/v,合成/v,材料/n,环保/j,性能/n,提出/v,有/v,针对性/n,要求/v,合同/n,形式/n,进行/v,约定/v,是/v,加强/v,新建/v,合成/v,材料/n,运动/n,场地/n,施工/vn,管理/vn,学校/n,工程/n,采用/v,材料/n,产品/n,工艺/n,应/v,进行/v,择定/nw,施工/vn,使用/v,关键/n,原材料/n,是/v,胶粒/n,胶水/n,溶剂/n,塑化剂/n,符合/v,国家/n,标准/n,要/v,加强/v,原料/n,合格/v,证书/n,检测/vn,报告/n,真实性/n,审查/v,确保/v,经得起/v,追溯/v,关键性/n,原材料/n,原则/n,应/v,进场/v,统一/vn,封存/v,委托/v,有/v,资质/n,权威/n,检测/vn,部门/n,国家/n,有关/vn,标准/n,物质/n,含量/n,进行/v,抽/v,检/v,抽检/v,合格/v,能/v,投入/v,使用/v,是/v,加强/v,新建/v,合成/v,材料/n,运动/n,场地/n,竣工/v,验收/vn,竣工/v,合成/v,材料/n,运动/n,场地/n,国家/n,规范/v,工程/n,结束/v,进行/v,竣工/v,验收/vn,学校/n,应/v,合同/n,约定/v,竣工/v,验收/vn,邀请/v,家长/n,教职工/n,代表/n,合成/v,材料/n,面层/nw,成品/n,取样/vn,委托/v,有/v,资质/n,权威/n,检测/vn,部门/n,有毒/v,物质/n,挥发性/n,气体/n,含量/n,进行/v,检测/vn,检测/vn,合格/v,项目/n,不得/v,进行/v,竣工/v,验收/vn,竣工/v,验收/vn,合格/v,合成/v,材料/n,运动/n,场地/n,要/v,安排/v,月/n,空置/v,时间/n,是/v,加强/v,合成/v,材料/n,运动/n,场地/n,使用/v,管理/vn,学校/n,实际/n,使用/v,合成/v,材料/n,运动/n,场地/n,时/ng,应/v,综合/vn,考虑/v,建成/v,时间/n,天气/n,室外/s,温度/n,因素/n,调整/vn,使用/v,时间/n,频率/n,室外/s,温度/n,时/ng,可/v,合成/v,材料/n,运动/n,场地/n,现场/s,气味/n,评价/v,办法/n,要求/v,上午/t,上课/v,下午/t,上课/v,合成/v,材料/n,运动/n,场地/n,气味/n,进行/v,评估/vn,评估/vn,结果/n,应/v,暂停/v,使用/v,督促/v,各校/nw,加强/v,合成/v,材料/n,运动/n,场地/n,管理/vn,工作/vn,此前/t,浙江省教育厅/nw,教育局/n,高校/j,下发/v,通知/n,要求/v,建成/v,合成/v,材料/n,运动/n,场地/n,进行/v,检查/vn,群众/n,有/v,反映/v,运动/n,场地/n,委托/v,权威/n,检测/vn,机构/n,进行/v,检测/vn,如/v,检测/vn,合格/v,停止/v,使用/v,进行/v,处理/v,今年/t,计划/n,建设/vn,项目/n,进行/v,评估/vn,能/v,确保/v,项目/n,停止/v,建设/vn,加强/v,合成/v,材料/n,运动/n,场地/n,使用/v,管理/vn,目前/t,全省/n,校正/v,要求/v,开展/v,检查/vn,评估/vn,工作/vn,浙江/ns,在线/vn,苏州姑/nw,苏区/n,校园/n,塑胶/n,跑道/n,近日/t,成为/v,家长/n,们/k,关注/v,热点/n,姑/dg,苏区/n,教育/vn,部门/n,回应/v,目前/t,学校/n,家长委员会/nw,监理/vn,单位/n,监督/vn,塑胶/n,跑道/n,进行/v,取样/vn,送/v,上海/ns,权威/n,机构/n,进行/v,检测/vn,预计/v,本月/t,中旬/t,得出/v,检测/vn,结果/n,近日/t,北京朝阳区兴隆小学/nw,出现/v,毒/n,跑道/n,危机/n,家长/n,提供/v,数据/n,小学/n,班级/n,参与/v,调查/vn,学生/n,出现/v,流/v,鼻血/n,红疹/nw,咳嗽/v,鼻炎/n,头晕/v,综合/vn,症状/n,家长委员会/nw,收集/v,医学/n,检查/vn,报告/n,血/n,细胞/n,指标/n,者/k,达到/v,维权/j,斗争/vn,学校/n,作出/v,拆除/v,跑道/n,承诺/v,";
//        System.out.println(RegexHandler.wordCombination(sentence));
    //    String source = "   录取 的，<br /><br />不影响下一批学校录取。●未被五年制高★职学校录取的，不影响其他中职学校录取。中职学校录取不分批次同时进行，但按考生志愿顺序投档录取。<br/><br/>问：今年报考中职、五年制高职院校艺术、体育类专业的考生必须参加专业考试吗？在哪里进行？<br/><br/>答：为加强中等职业学校、五年制高职院校艺术、体育类专业招生工作的管理，完善现行的艺、体考试办法，按省招考院统一制定的中职、五年制高职院校艺术、体育类考试大纲，由各州、市招生考试机构组织专业考试。专业考试成绩须与文化成绩一并录入信息管理系统。<br/><br/>填报中职、五年制高职院校艺术、体育类专业考生，都必须参加所在州、市组织的专业考试。只有参加了专业考试的考生才可以填报艺术、体育类专业。<br/><br/>问：没参加初中学生学业水平考试的往届初中毕业生怎样报读中职学校？<br/><br/>答：须到户口所在地招考机构报名、办理相关手续，获得13位数的信息登记编号，参加中职学校正常录取；也可以直接到拟就读学校报名，并参加全省统一组织的补报志愿后，由学校统一到招生考试机构办理录取手续。<br/><br/>问：未被录取的学生能否补报志愿?<br/><br/>答：能。未被录取的学生在录取工作后期，省招考院根据录取情况，组织还未完成招生计划的学校进行补录，并在网上发布补录信息。考生可上网查询录取情况，未被录取的考生关注补报志愿的时间，在补报志愿期限内到所在地招生考试机构补报志愿，参加相关学校后期补录。<br/><br/>邹佳琪";
      //  System.out.println(RegexHandler.removeTagger(source,RegexHandler.REGEX_OF_HTML_TAGGER,REPLACE_BY_NOTHING));
    }
}
