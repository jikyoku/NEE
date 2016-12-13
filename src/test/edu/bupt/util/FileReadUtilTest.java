package test.edu.bupt.util; 

import edu.bupt.util.FileReadUtil;
import junit.framework.Test;
import junit.framework.TestSuite; 
import junit.framework.TestCase;

import java.util.List;

/** 
* FileReadUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>08/07/2016</pre> 
* @version 1.0 
*/ 
public class FileReadUtilTest extends TestCase { 
public FileReadUtilTest(String name) { 
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
* Method: readDict(String path) 
* 
*/ 
public void testReadDict() throws Exception { 
//TODO: Test goes here...
    String path = "event_extra/dict";
    List<String> words = FileReadUtil.readDict(path);
    System.out.println(words);
}

/** 
* 
* Method: main(String[] args) 
* 
*/ 
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: readFile(String path) 
* 
*/ 
public void testReadFile() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = FileReadUtil.getClass().getMethod("readFile", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 


public static Test suite() { 
return new TestSuite(FileReadUtilTest.class); 
} 
} 
