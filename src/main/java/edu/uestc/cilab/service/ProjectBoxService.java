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
     * @author zhangfeng 作者
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
     * @param projectBox 文书档案盒子
     * @author zhangfeng 作者
     */
    void update(ProjectBox projectBox)throws ExistException ;

    /**
     * 查询工程档案盒子
     *
     * @author zhangfeng 作者
     */
    PageUtil<ProjectBox> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,String projectName, String boxNumber);

    /**
     * 查询所有项目名称列表
     *
     * @author zhangfeng
     */
    List<ProjectBox> selectProjectList();

    /**
     * 获取指定项目名称条件下的列表
     * @param projectName
     * @return
     */
    List<ProjectBox> getProjectListByName ( String projectName);
}

