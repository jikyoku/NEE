package edu.bupt.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * Created by shi xu on 2016-07-25.
 *
 */
public class SessionFactoryUtil {

    private static SqlSessionFactory sqlSessionFactory = null;
    private SessionFactoryUtil(){}
    public static SqlSessionFactory getSessionFactory() {
        if (sqlSessionFactory == null){
            try {
                Reader  reader = Resources.getResourceAsReader("Configuration.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSeeion(){
        return getSessionFactory().openSession();
    }
}
