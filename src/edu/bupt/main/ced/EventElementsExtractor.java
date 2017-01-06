package edu.bupt.main.ced;

import edu.bupt.http.HttpRequest;
import edu.bupt.model.Argument;
import edu.bupt.model.Event;
import edu.bupt.model.Sentence;
import edu.bupt.util.json.JsonParser;
import org.json.JSONArray;
import edu.bupt.model.Word;

import java.util.List;

/**
 * Created by shi xu on 2016/12/14.
 * 事件元素抽取
 */
public class EventElementsExtractor {
    public static void srl(Sentence sentence) {
        List<Word> wordList = sentence.getWordList();
        Event event = new Event();
        for (Word word :
                wordList) {
            if (word.getRelate().equals("HED")) {
                System.out.println("核心词：" + word.getCont());
                event.setHED(word.getCont());
                List<Argument> arguments = word.getArguments();
                if (arguments == null)
                    continue;
                for (Argument argument :
                        arguments) {
                    switch (argument.getType()) {
                        case "TMP":
                            StringBuilder tmp = new StringBuilder();
                            for (int i = argument.getBeg(); i <= argument.getEnd(); i++) {
                                tmp.append(wordList.get(i).getCont());
                            }
                            event.setTMP(tmp.toString());
                            System.out.println("When：" + tmp.toString());
                            break;
                        case "A0":
                            StringBuilder subject = new StringBuilder();
                            for (int i = argument.getBeg(); i <= argument.getEnd(); i++) {
                                subject.append(wordList.get(i).getCont());
                            }
                            event.setA0(subject.toString());
                            System.out.println("A0：" + subject.toString());
                            break;
                        case "A1":
                            StringBuilder subject1 = new StringBuilder();
                            for (int i = argument.getBeg(); i <= argument.getEnd(); i++) {
                                subject1.append(wordList.get(i).getCont());
                            }
                            event.setA1(subject1.toString());
                            System.out.println("A1：" + subject1.toString());
                            break;
                        case "LOC":
                            StringBuilder loc = new StringBuilder();
                            for (int i = argument.getBeg(); i <= argument.getEnd(); i++) {
                                loc.append(wordList.get(i).getCont());
                            }
                            event.setLOC(loc.toString());
                            System.out.println("Loc：" + loc.toString());
                            break;
                    }
                }
            }
        }
        sentence.setEvent(event);
    }


    public static void main(String[] args) {
        Sentence sentence = new Sentence("今天下午5点，中国国家奥委会主席、国家体育总局局长李兴，在召开的大会上强调，宁愿牺牲奖牌，也要办一次干干净净的奥运会");
        String strJson = HttpRequest.xfyunHttpApi(sentence.getSentenceContent(), HttpRequest.PATTERN_SRL, HttpRequest.FORMAT_JSON);
        System.out.println(strJson);
        JSONArray jsonArray = JsonParser.getJsonArrayByStr(strJson);
        List<Word> wordList = JsonParser.parseJsonArrayToWords(jsonArray);
        sentence.setWordList(wordList);
        EventElementsExtractor.srl(sentence);
    }
}