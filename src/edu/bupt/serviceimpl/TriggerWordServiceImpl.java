package edu.bupt.serviceimpl;

import edu.bupt.mapper.TriggerWordMapper;
import edu.bupt.mapperimpl.TriggerWordMapperImpl;
import edu.bupt.model.TriggerWord;
import edu.bupt.service.TriggerWordService;

import java.util.List;
import java.util.Map;

/**
 * Created by shi xu on 2016-07-30.
 *
 * @author shixu
 */
public class TriggerWordServiceImpl implements TriggerWordService {

    TriggerWordMapper triggerWordMapper = new TriggerWordMapperImpl();
    @Override
    public List<TriggerWord> getListOfTriggerWord() {
        return triggerWordMapper.getListOfTriggerWord();
    }

    @Override
    public Map<String, TriggerWord> getMapOfTriggerWord() {
        return triggerWordMapper.getMapOfTriggerWord();
    }

    @Override
    public int addTrgWd(TriggerWord triggerWord) {
        return triggerWordMapper.addTrgWd(triggerWord);
    }

    @Override
    public List<TriggerWord> getTwAndSubType() {
        return triggerWordMapper.getTwAndSubType();
    }

    @Override
    public List<TriggerWord> getTWByName(String value) {
        return triggerWordMapper.getTWByName(value);
    }
}
