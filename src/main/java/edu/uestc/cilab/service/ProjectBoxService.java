package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.ProjectBox;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;

import java.util.List;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 工程盒子接口
 **/
public interface ProjectBoxService {
    /**
     * 添加工程档案盒子
     *
     * @param  projectBox 文书档案盒子
     * @throws  ExistException
     */
    void add(ProjectBox projectBox) throws ExistException;
    /**
     * 删除工程档案盒子
     *
     * @param id 工程档案盒子id
     * @author zhangfeng 作者
     */
    void delete(Integer id);

    /**
     * 更新工程档案盒子
     *
     * @param projectBox 文书档案盒子
     * @throws  ExistException
     */
    void update(ProjectBox projectBox)throws ExistException ;

    /**
     * 查询工程档案盒子
     *
     * @param pageNumber 页码
     * @param pageSize 页面大小
     * @param sortName 排序字段
     * @param sortOrder 排序方式
     * @param projectName 工程名称
     * @param boxNumber 盒号
     * @return PageUtil<ProjectBox>
     */
    PageUtil<ProjectBox> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,String projectName, String boxNumber);

    /**
     * 查询所有项目名称列表
     *
     * @return List<ProjectBox>
     */
    List<ProjectBox> selectProjectList();

    /**
     * 获取指定项目名称条件下的列表
     * @param projectName 工程名称
     * @return List<ProjectBox>
     */
    List<ProjectBox> getProjectListByName ( String projectName);
}

