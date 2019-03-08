package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.ProjectDocument;
import edu.uestc.cilab.entity.vo.ProjectDocumentExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;

import java.util.List;

/**
 * Created by zhangfeng on 2018/1/15.
 */
public interface ProjectDocumentService {
    /**
     * 添加工程档案
     *
     * @param projectDocument 工程档案
     * @author zhangfeng 作者
     */
    void add(ProjectDocument projectDocument)  throws ExistException;

    /**
     * 删除用户
     *
     * @param id 工程档案id
     * @author zhangfeng 作者
     */
    void delete(Integer id);

    /**
     * 更新工程档案
     * @param projectDocument 用户
     * @author zhangfeng 作者
     */
    void update(ProjectDocument projectDocument);

    /**
     * 查询工程档案
     *
     * @author zhangfeng 作者
     */
    PageUtil<ProjectDocument> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,Integer projectBoxId,String boxNumber,String number, String responsiblePerson, String title, String keepTime, String createUserName);

    /**
     * 查询工程档案
     *
     * @author zhangfeng 作者
     */
    PageUtil<ProjectDocument> selectTotal(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,String projectName,String boxNumber,String number, String responsiblePerson, String title, String keepTime, String createUserName);

    /**
     * @author zhangfeng 作者
     * @return
     */
    List<ProjectDocumentExcelVo> getAllData(Integer projectBoxId);
}
