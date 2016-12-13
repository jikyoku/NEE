package edu.bupt.serviceimpl;

import edu.bupt.mapper.SubEventMapper;
import edu.bupt.model.TriggerWord;
import edu.bupt.service.EntityService;
import edu.bupt.service.NewsService;
import edu.bupt.service.TriggerWordService;
import edu.bupt.util.SessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by shixu on 2016-08-02.
 */
public class Test {
    public static void main(String[] args) {
        SqlSession session = SessionFactoryUtil.getSessionFactory().openSession();
        SubEventMapper subEventMapper = session.getMapper(SubEventMapper.class);
        int id = subEventMapper.getIdByName("培养教育");
        System.out.println(id);

        TriggerWordService triggerWordService = new TriggerWordServiceImpl();
        List<TriggerWord> list =  triggerWordService.getTWByName("长");
        System.out.println(list);


        EntityService entityService = new EntityServiceImpl();
        System.out.println(entityService.getEntityById(140079));


        NewsService newsService = new NewsServiceImpl();
        System.out.println(newsService.getNewsById(7));
    }
}
