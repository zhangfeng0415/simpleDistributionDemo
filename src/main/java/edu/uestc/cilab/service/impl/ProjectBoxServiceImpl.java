package edu.uestc.cilab.service.impl;

import com.github.pagehelper.PageHelper;
import edu.uestc.cilab.entity.ProjectBox;
import edu.uestc.cilab.entity.ProjectBoxExample;
import edu.uestc.cilab.entity.ProjectDocument;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.repository.ProjectBoxMapper;
import edu.uestc.cilab.repository.ProjectDocumentMapper;
import edu.uestc.cilab.service.ProjectBoxService;
import edu.uestc.cilab.service.ProjectDocumentService;
import edu.uestc.cilab.util.DBUtil;
import edu.uestc.cilab.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 工程盒子接口实现层
 **/
@Service
public class ProjectBoxServiceImpl implements ProjectBoxService{

    @Autowired
    ProjectBoxMapper projectBoxMapper;
    @Autowired
    ProjectDocumentMapper projectDocumentMapper;
    @Autowired
    ProjectDocumentService projectDocumentService;

    @Override
    public void add(ProjectBox projectBox) throws ExistException {
        if (0 != projectBoxMapper.getSameNumber(projectBox.getProjectName(), projectBox.getBoxNumber())){
            throw new ExistException("项目名称与盒号重复");
        }
        projectBoxMapper.insertSelective(projectBox);
    }

    @Override
    public void delete(Integer id) {
        List<ProjectDocument> projectDocuments = projectDocumentMapper.selectAllData(id);
        for (ProjectDocument projectDocument:projectDocuments) {
            projectDocumentService.delete(projectDocument.getId());
        }
        projectBoxMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProjectBox projectBox) throws ExistException {
        ProjectBox projectBox1  = projectBoxMapper.selectByPrimaryKey(projectBox.getId());

        //如果名称与盒号都相同则直接修改
        if (projectBox1.getProjectName().equals(projectBox.getProjectName()) && projectBox1.getBoxNumber().equals(projectBox.getBoxNumber()))
        {
            projectBoxMapper.updateByPrimaryKeySelective(projectBox);
        }
        else {
            if (0 != projectBoxMapper.getSameNumber(projectBox.getProjectName(), projectBox.getBoxNumber())){
                //有重复则报错
                throw new ExistException("项目名称与盒号重复");
            }
            if (projectBox1.getBoxNumber() != projectBox.getBoxNumber()){
                //无重复但盒号变了
                projectDocumentMapper.updateBoxNumberByProjectBoxId(projectBox.getId(),projectBox.getBoxNumber());
            }
            projectBoxMapper.updateByPrimaryKeySelective(projectBox);
        }
    }

    @Override
    public PageUtil<ProjectBox> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, String projectName, String boxNumber) {
        PageHelper.startPage(pageNumber, pageSize);
        ProjectBoxExample projectBoxExample = new ProjectBoxExample();
        ProjectBoxExample.Criteria criteria = projectBoxExample.createCriteria();
        if (projectName != null){criteria.andProjectNameLike("%" + projectName+ "%");}
        if (boxNumber != null){criteria.andBoxNumberEqualTo(boxNumber); }
        String orderByClause = DBUtil.getColumNameByProperty(sortName) + " " + sortOrder;
        projectBoxExample.setOrderByClause(orderByClause);
        List<ProjectBox> projectBoxes = projectBoxMapper.selectByExample(projectBoxExample);

        return new PageUtil<>(pageNumber, pageSize, projectBoxMapper.countByExample(projectBoxExample), projectBoxes);
    }

    @Override
    public List<ProjectBox> selectProjectList() {
        List<ProjectBox> projectBoxes = projectBoxMapper.getProjectNameList();
        return projectBoxes;
    }

    @Override
    public List<ProjectBox> getProjectListByName(String projectName) {
        List<ProjectBox> projectBoxes = projectBoxMapper.getProjectNameListByName(projectName);
        return projectBoxes;
    }
}
