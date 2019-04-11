package edu.uestc.cilab.service.impl;

import edu.uestc.cilab.entity.ProjectContent;
import edu.uestc.cilab.repository.ProjectContentMapper;
import edu.uestc.cilab.service.ProjectContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 工程内容接口实现层
 **/
@Service
public class ProjectContentServiceImpl implements ProjectContentService{
    @Autowired
    ProjectContentMapper projectContentMapper;

    @Override
    public void add(ProjectContent projectContent) {
        projectContentMapper.insertSelective(projectContent);
    }

    @Override
    public void delete(Integer id) {
        ProjectContent projectContent = projectContentMapper.selectByPrimaryKey(id);
        if(!projectContent.getContentAddress().equals("")){
            String deletePath = projectContent.getContentAddress();
            File file = new File(deletePath);
            file.delete();
        }
        projectContentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ProjectContent> getAllData(Integer projectDocumentId) {
        return projectContentMapper.selectAlldata(projectDocumentId);
    }
}
