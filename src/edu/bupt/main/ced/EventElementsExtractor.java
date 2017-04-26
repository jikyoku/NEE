package edu.bupt.main.ced;

import edu.bupt.http.HttpRequest;
import edu.bupt.model.Argument;
import edu.bupt.model.Event;
import edu.bupt.model.Sentence;
import edu.bupt.util.json.JsonParser;
import org.json.JSONArray;
import edu.bupt.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shi xu on 2016/12/14.
 * 事件元素抽取
 */
public class EventElementsExtractor {
    private List<Event> events = new ArrayList<>();
    private List<Word> wordList;
    private Sentence sentence;

    public EventElementsExtractor(Sentence sentence) {
        this.sentence = sentence;
        this.wordList = sentence.getWordList();
    }

    public void eeehandler() {
        createEvent();
        setEvent();
       // System.out.println(events);
    }

    /**
     * 先抽取事件（核心词是动词或者是动宾关系）
     */
    private void createEvent() {
        for (Word word :
                wordList) {
            Event event = null;
            if (word.getPos().equals("v")) { //能表述事件的词必须是动词
                if (word.getRelate().equals("HED")) { //如果是动词同时又是核心词
                    event = new Event();
                    event.setHED(word);
                    event.setAction(word);
                } else if (word.getRelate().equals("VOB")) { //如果是动词又是其他动词的宾语，则这个动词也表述一个事件
                    event = new Event();
                    event.setAction(word);
                }
            }
            if (event != null)
                events.add(event);
        }

    }

    /**
     * 设置事件参数
     */
    public void setEvent() {
        for (Event event :
                events) {
            List<Argument> argument = event.getAction().getArguments();
            if (argument != null)
                setEventArgument(wordList, event, argument);
            if (event.getA0() == null)
                setA0(wordList, event);
            if (event.getA1() == null)
                setA1(wordList, event);
        }
        sentence.setEventList(events);
    }

    public void setA1(List<Word> wordList, Event event) {
        for (Word word :
                wordList) {
            if (word.getParent() == event.getAction().getId()) { //如果当前词对某个事件核心词有依存关系
                if (word.getRelate().equals("VOB")) { //如果是主谓关系
                    StringBuilder A0Str = getFullATT(wordList, word).append(word.getCont());
                    event.setA1(A0Str.toString());
                    break;
                }
            }
        }
    }

    public void setA0(List<Word> wordList, Event event) {
        for (Word word :
                wordList) {
            if (word.getParent() == event.getAction().getId()) { //如果当前词对某个事件核心词有依存关系
                if (word.getRelate().equals("SBV")) { //如果是主谓关系
                    StringBuilder A0Str = getFullATT(wordList, word).append(word.getCont());
                    event.setA0(A0Str.toString());
                    break;
                }
            }
        }

    }

    public void srl() {
        List<Word> wordList = sentence.getWordList();
        Event event = null;
        for (Word word :
                wordList) {
            List<Argument> arguments = word.getArguments();
//            if (arguments == null)
//                continue;
            event = new Event();
            if (word.getPos().equals("v") && arguments != null) {
                if (word.getRelate().equals("HED")) { //核心词
                    event.setHED(word);
                    event.setAction(word);
                    setEventArgument(wordList, event, arguments);
                    events.add(event);
                    continue;
                }
                if (word.getRelate().equals("VOB")) {  //谓语动词 并且构成动宾关系
                    event.setAction(word);
                    setEventArgument(wordList, event, arguments);
                    events.add(event);
                }
            } else {
                if (word.getRelate().equals("VOB")) {
                    int parentId = word.getParent();
                    Word parent = wordList.get(parentId);
                    for (Event eventTmp :
                            events) {
                        if (eventTmp.getAction().equals(parent.getCont())) {
                            eventTmp.setA1(getFullATT(wordList, word).append(word.getCont()).toString());
                            break;
                        }
                    }
                } else if (word.getRelate().equals("SBV") && word.getRelate().equals("VOB")) {
                    int parentId = word.getParent();
                    Word parent = wordList.get(parentId);
                    for (Event eventTmp :
                            events) {
                        if (eventTmp.getAction().equals(parent.getCont())) {
                            eventTmp.setA0(getFullATT(wordList, word).append(word.getCont()).toString());
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("events:" + events);
        sentence.setEvent(event);
        sentence.setEventList(events);
    }

    /**
     * 设置事件元素
     *
     * @param wordList  词列表
     * @param event     事件对象
     * @param arguments 语义角色列表
     */
    private void setEventArgument(List<Word> wordList, Event event, List<Argument> arguments) {
        for (Argument argument :
                arguments) {
            switch (argument.getType()) {
                case "TMP":
                    StringBuilder tmp = getArgument(wordList, argument);
                    event.setTMP(tmp.toString());
                    break;
                case "A0":
                    StringBuilder subject = getArgument(wordList, argument);
                    String a0 = event.getA0();
                    event.setA0(subject.toString() + (a0 == null ? "" : a0));
                    break;
                case "A1":
                    StringBuilder subject1 = getArgument(wordList, argument);
                    event.setA1(subject1.toString());
                    break;
                case "LOC":
                    StringBuilder loc = getArgument(wordList, argument);
                    event.setLOC(loc.toString());
                    break;
            }
        }
    }

    private static StringBuilder getFullATT(List<Word> wordList, Word word) {
        StringBuilder fullATT = new StringBuilder("");
        for (Word wordTmp :
                wordList) {
            if (wordTmp.getRelate().equals("ATT") && wordTmp.getParent() == word.getId()) {
                fullATT.append(wordTmp.getCont());
                fullATT.append(getFullATT(wordList, wordTmp));
            }
        }
        return fullATT;
    }


    private static StringBuilder getArgument(List<Word> wordList, Argument argument) {
        StringBuilder tmp = new StringBuilder();
        for (int i = argument.getBeg(); i <= argument.getEnd(); i++) {
            tmp.append(wordList.get(i).getCont());
        }
        return tmp;
    }



    public static void main(String[] args) {
        Sentence sentence = new Sentence("今天下午5点，中国国家奥委会主席、国家体育总局局长李兴，在召开的大会上强调，宁愿牺牲奖牌，也要办一次干干净净的奥运会");
        sentence.setSentenceContent("人民网北京1月13日电（记者申宁）记者从教育部官微了解到，教育部、国家体育总局、共青团中央决定于2017年9月4日—16日，在浙江省杭州市举办中华人民共和国第十三届学生运动会，这也是我国首次将大、中学生运动会合并后举行的一次全国性学校体育重大活动。");
        //sentence.setSentenceContent("人民网北京1月12日电 （记者林露）近日，浙江省教育考试院公布2017年新高考招生录取方案，今年将首次实行与之相配套的新招生录取办法。");
        //sentence.setSentenceContent("人民网北京1月13日电（记者申宁）记者从教育部官微了解到，2017上半年中小学教师资格考试（笔试）于1月13日至16日期间进行网上报名，考生在此期间可登录中小学教师资格考试网报名。");
        String strJson = HttpRequest.xfyunHttpApi(sentence.getSentenceContent(), HttpRequest.PATTERN_SRL, HttpRequest.FORMAT_JSON);
        JSONArray jsonArray = JsonParser.getJsonArrayByStr(strJson);
        List<Word> wordList = JsonParser.parseJsonArrayToWords(jsonArray);
        sentence.setWordList(wordList);
        System.out.println(sentence.getSentenceContent());
        EntityExtractor.entityExtract(sentence);
        EventElementsExtractor eee = new EventElementsExtractor(sentence);
        eee.eeehandler();
        System.out.println(sentence.getEntityList());
        System.out.println(sentence.getEvent());
    }
}