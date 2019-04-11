package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.ProjectContent;

import java.util.List;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 工程内容接口
 **/
public interface ProjectContentService {
    /**
     * 添加工程档案内容
     *
     * @param projectContent 工程档案内容
     * @author zhangfeng 作者
     */
    void add(ProjectContent projectContent);

    /**
     * 删除工程档案内容
     *
     * @param id 工程档案内容id
     * @author zhangfeng 作者
     */
    void delete(Integer id);

    /**
     * 获取指定工程档案id下所有图片内容地址
     * @param projectDocumentId  工程档案id
     * @author zhangfeng 作者
     * @return
     */
    List<ProjectContent> getAllData(Integer projectDocumentId);
}
