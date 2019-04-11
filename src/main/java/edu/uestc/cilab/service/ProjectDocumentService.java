package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.ProjectDocument;
import edu.uestc.cilab.entity.vo.ProjectDocumentExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;

import java.util.List;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 工程文件接口
 **/
public interface ProjectDocumentService {
    /**
     * 添加工程档案
     *
     * @param projectDocument 工程档案
     * @throws  ExistException
     */
    void add(ProjectDocument projectDocument)  throws ExistException;

    /**
     * 删除用户
     *
     * @param id 工程档案id
     */
    void delete(Integer id);

    /**
     * 更新工程档案
     *
     * @param projectDocument 工程档案
     */
    void update(ProjectDocument projectDocument);

    /**
     * 查询工程档案
     *
     * @param pageNumber 页码
     * @param pageSize 页面大小
     * @param sortName 排序字段
     * @param sortOrder 排序方式
     * @param projectBoxId 工程盒号id
     * @param boxNumber 盒号
     * @param number 编号
     * @param responsiblePerson 责任人
     * @param title 标题
     * @param keepTime 保存时间
     * @param createUserName 创造人名称
     * @return PageUtil<ProjectDocument>
     */
    PageUtil<ProjectDocument> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,Integer projectBoxId,String boxNumber,String number, String responsiblePerson, String title, String keepTime, String createUserName);

    /**
     * 查询工程档案
     *
     * @param pageNumber 页码
     * @param pageSize 页面大小
     * @param sortName 排序字段
     * @param sortOrder 排序方式
     * @param projectName 工程名称
     * @param boxNumber 盒号
     * @param number 编号
     * @param responsiblePerson 责任人
     * @param title 标题
     * @param keepTime 保存时间
     * @param createUserName 创造人名称
     * @return PageUtil<ProjectDocument>
     */
    PageUtil<ProjectDocument> selectTotal(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,String projectName,String boxNumber,String number, String responsiblePerson, String title, String keepTime, String createUserName);

    /**
     * 获取所有工程档案信息并转换成Excel格式数据
     *
     * @param projectBoxId 工程盒号id
     * @return List<ProjectDocumentExcelVo>
     */
    List<ProjectDocumentExcelVo> getAllData(Integer projectBoxId);
}
