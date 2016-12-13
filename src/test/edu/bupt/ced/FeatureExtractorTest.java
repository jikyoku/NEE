package test.edu.bupt.ced; 

import edu.bupt.main.ced.FeatureExtractor;
import edu.bupt.model.Feature;
import edu.bupt.model.Sentence;
import junit.framework.Test;
import junit.framework.TestSuite; 
import junit.framework.TestCase;

import java.util.Arrays;

/** 
* FeatureExtractor Tester.
* 
* @author <Authors name> 
* @since <pre>08/06/2016</pre> 
* @version 1.0 
*/ 
public class FeatureExtractorTest extends TestCase {
public FeatureExtractorTest(String name) {
super(name); 
} 

public void setUp() throws Exception { 
super.setUp(); 
} 

public void tearDown() throws Exception { 
super.tearDown(); 
} 

/** 
* 
* Method: preProcessOfSentence(Sentence sentence) 
* 
*/ 
public void testPreProcessOfSentence() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: wordTaggerSeparation(Sentence sentence) 
* 
*/ 
public void testWordTaggerSeparation() throws Exception { 
//TODO: Test goes here...
    Sentence sentence = new Sentence();
    sentence.setSentenceContent("课堂/n作业/vn1./m类似/v应用题/n200/m可用/v相同/a方法/n求解/v2./m填空/v3./m统计/v图表/n现场/s家长/n冒雨/v等候/v气氛/n堪/vg比/p高考/v一大早/t家长/n们/k把/p孩子/n送/v进/v学校/n后/f便/d聚/v在/p一起/s聊天/v言谈/n之间/f颇/d为/p焦虑/a等候/v家长/n人数/n和/c气氛/n堪/vg比/p高考/v");
//    FeatureExtractor.wordTaggerSeparation(sentence);
    System.out.println(Arrays.toString(sentence.getTaggers()));
    System.out.println(Arrays.toString(sentence.getWords()));
    System.out.println(sentence.getWords().length+"---"+sentence.getTaggers().length);

} 

/** 
* 
* Method: entityExtraction(Sentence sentence) 
* 
*/ 
public void testEntityExtraction() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: featureExtraction(Sentence sentence) 
* 
*/ 
public void testFeatureExtraction() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isRealEvent(Feature feature) 
* 
*/ 
public void testIsRealEvent() throws Exception { 
//TODO: Test goes here...
    Feature feature = new Feature("教育","/vn", "/v", "/nt", "/v", "/v", "/n", "/c", "/j", "/v", "4", "4");
    System.out.println(feature);
    FeatureExtractor.isRealEvent(feature);


} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 



public static Test suite() { 
return new TestSuite(FeatureExtractorTest.class);
} 
} 
