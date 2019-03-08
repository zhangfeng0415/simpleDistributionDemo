package edu.uestc.cilab.service.impl;

import edu.uestc.cilab.entity.OfficialContent;
import edu.uestc.cilab.repository.OfficialContentMapper;
import edu.uestc.cilab.service.OfficialContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by zhangfeng on 2018/1/15.
 */
@Service
public class OfficialContentServiceImpl implements OfficialContentService{
    @Autowired
    OfficialContentMapper officialContentMapper;
    @Override
    public void add(OfficialContent officialContent) {
        officialContentMapper.insertSelective(officialContent);
    }

    @Override
    public void delete(Integer id) {
        OfficialContent officialContent = officialContentMapper.selectByPrimaryKey(id);
        if(!officialContent.getContentAddress().equals("")){
            String deletePath = officialContent.getContentAddress();
            File file = new File(deletePath);
            file.delete();
        }
        officialContentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<OfficialContent> getAllData(Integer officialDocumentId) {
        return officialContentMapper.selectAlldata(officialDocumentId);
    }
}
