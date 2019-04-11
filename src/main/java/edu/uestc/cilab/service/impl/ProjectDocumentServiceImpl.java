package edu.uestc.cilab.service.impl;

import com.github.pagehelper.PageHelper;
import edu.uestc.cilab.entity.ProjectBox;
import edu.uestc.cilab.entity.ProjectContent;
import edu.uestc.cilab.entity.ProjectDocument;
import edu.uestc.cilab.entity.ProjectDocumentExample;
import edu.uestc.cilab.entity.vo.ProjectDocumentExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.repository.ProjectBoxMapper;
import edu.uestc.cilab.repository.ProjectContentMapper;
import edu.uestc.cilab.repository.ProjectDocumentMapper;
import edu.uestc.cilab.service.ProjectContentService;
import edu.uestc.cilab.service.ProjectDocumentService;
import edu.uestc.cilab.util.DBUtil;
import edu.uestc.cilab.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 工程文件接口实现层
 **/
@Service
public class ProjectDocumentServiceImpl implements ProjectDocumentService{
    @Autowired
    ProjectDocumentMapper projectDocumentMapper;
    @Autowired
    ProjectBoxMapper projectBoxMapper;
    @Autowired
    ProjectContentMapper projectContentMapper;
    @Autowired
    ProjectContentService projectContentService;
    @Override
    public void add(ProjectDocument projectDocument) throws ExistException {
//        if (0 != projectDocumentMapper.getSameNumber(projectDocument.getNumber(),projectDocument.getProjectBoxId())){
//            throw new ExistException("盒号与档案编号重复");
//        }
        projectDocumentMapper.insertSelective(projectDocument);
    }

    @Override
    public void delete(Integer id) {
        List<ProjectContent> projectContents = projectContentMapper.selectAlldata(id);
        for (ProjectContent projectContent:projectContents) {
            projectContentService.delete(projectContent.getId());
        }
        projectDocumentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProjectDocument projectDocument) {
        projectDocumentMapper.updateByPrimaryKeySelective(projectDocument);
    }

    @Override
    public PageUtil<ProjectDocument> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Integer projectBoxId, String boxNumber, String number, String responsiblePerson, String title, String keepTime, String createUserName) {
        PageHelper.startPage(pageNumber, pageSize);
        ProjectDocumentExample projectDocumentExample = new ProjectDocumentExample();
        ProjectDocumentExample.Criteria criteria = projectDocumentExample.createCriteria();
        String  percent = "%";
        if (projectBoxId != null) {criteria.andProjectBoxIdEqualTo(projectBoxId);}
        if (boxNumber != null){criteria.andBoxNumberEqualTo(boxNumber); }
        if (number != null) {criteria.andNumberLike(percent+ number +percent);}
        if (responsiblePerson != null){criteria.andResponsiblePersonLike(percent+ responsiblePerson + percent);}
        if (title != null){criteria.andTitleLike(percent+ title + percent);}
        if (keepTime != null){criteria.andKeepTimeLike(percent+ keepTime +percent);}
        if (createUserName != null){criteria.andCreateUserNameLike(percent+ createUserName +percent);}
        String orderByClause = DBUtil.getColumNameByProperty(sortName) + " " + sortOrder;
        projectDocumentExample.setOrderByClause(orderByClause);
        List<ProjectDocument> projectDocuments = projectDocumentMapper.selectByExample(projectDocumentExample);

        return new PageUtil<>(pageNumber,pageSize,projectDocumentMapper.countByExample(projectDocumentExample),projectDocuments);
    }

    @Override
    public PageUtil<ProjectDocument> selectTotal(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, String projectName, String boxNumber, String number, String responsiblePerson, String title, String keepTime, String createUserName) {
        List<ProjectBox> projectBoxes = projectBoxMapper.getList(projectName);
        List<ProjectDocument> projectDocuments = new ArrayList<>();
        Integer count = 0;
        for (ProjectBox  projectBox:projectBoxes) {
            PageHelper.startPage(pageNumber, pageSize);
            ProjectDocumentExample projectDocumentExample = new ProjectDocumentExample();
            ProjectDocumentExample.Criteria criteria = projectDocumentExample.createCriteria();
            Integer projectBoxId = projectBox.getId();
            String  percent = "%";
            if (projectBoxId != null) {criteria.andProjectBoxIdEqualTo(projectBoxId);}
            if (boxNumber != null){criteria.andBoxNumberEqualTo(boxNumber); }
            if (number != null) {criteria.andNumberLike(percent+ number +percent);}
            if (responsiblePerson != null){criteria.andResponsiblePersonLike(percent+ responsiblePerson + percent);}
            if (title != null){criteria.andTitleLike(percent+ title + percent);}
            if (keepTime != null){criteria.andKeepTimeLike(percent+ keepTime +percent);}
            if (createUserName != null){criteria.andCreateUserNameLike(percent+ createUserName +percent);}
            String orderByClause = DBUtil.getColumNameByProperty(sortName) + " " + sortOrder;
            projectDocumentExample.setOrderByClause(orderByClause);
            List<ProjectDocument> projectDocument = projectDocumentMapper.selectByExample(projectDocumentExample);
            count = count + projectDocumentMapper.countByExample(projectDocumentExample);
            projectDocuments.addAll(projectDocument);
        }
        return new PageUtil<>(pageNumber,pageSize,count,projectDocuments);
    }

    @Override
    public List<ProjectDocumentExcelVo> getAllData(Integer projectBoxId) {
        List<ProjectDocument> projectDocumentList = projectDocumentMapper.selectAllData(projectBoxId);
        List<ProjectDocumentExcelVo> projectDocumentExcelVoList = new ArrayList<>();
        for (ProjectDocument projectDocument:projectDocumentList) {
            ProjectDocumentExcelVo projectDocumentExcelVo = new ProjectDocumentExcelVo();
            projectDocumentExcelVo.setId(projectDocument.getId());
            projectDocumentExcelVo.setBox_number(projectDocument.getBoxNumber());
            projectDocumentExcelVo.setNumber(projectDocument.getNumber());
            projectDocumentExcelVo.setResponsiblePerson(projectDocument.getResponsiblePerson());
            projectDocumentExcelVo.setTitle(projectDocument.getTitle());
            projectDocumentExcelVo.setDocumentTime(projectDocument.getDocumentTime());
            projectDocumentExcelVo.setPageNumber(projectDocument.getPageNumber());
            projectDocumentExcelVo.setRemark(null);
            projectDocumentExcelVoList.add(projectDocumentExcelVo);
        }
        return projectDocumentExcelVoList;
    }
}
