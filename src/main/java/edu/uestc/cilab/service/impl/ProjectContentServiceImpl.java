package edu.uestc.cilab.service.impl;

import edu.uestc.cilab.entity.ProjectContent;
import edu.uestc.cilab.repository.ProjectContentMapper;
import edu.uestc.cilab.service.ProjectContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by zhangfeng on 2018/1/15.
 */
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
