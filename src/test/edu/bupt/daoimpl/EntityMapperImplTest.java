package test.edu.bupt.daoimpl; 

import edu.bupt.mapperimpl.EntityMapperImpl;
import edu.bupt.model.Entity;
import junit.framework.Test;
import junit.framework.TestSuite; 
import junit.framework.TestCase; 

/** 
* EntityMapperImpl Tester.
* 
* @author <Authors name> 
* @since <pre>08/05/2016</pre> 
* @version 1.0 
*/ 
public class EntityMapperImplTest extends TestCase {
public EntityMapperImplTest(String name) {
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
* Method: addEntityBatch(List<Entity> list) 
* 
*/ 
public void testAddEntityBatch() throws Exception { 
//TODO: Test goes here...


} 

/** 
* 
* Method: getEntityById(int id) 
* 
*/ 
public void testGetEntityById() throws Exception { 
//TODO: Test goes here...
    EntityMapperImpl entityDAO = new EntityMapperImpl();
    Entity entity = entityDAO.getEntityById(149129);
    System.out.println(entity);
} 



public static Test suite() { 
return new TestSuite(EntityMapperImplTest.class);
} 
} 
