package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.OfficialContent;
import io.swagger.models.auth.In;

import java.util.List;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 文书内容接口
 **/
public interface OfficialContentService {
    /**
     * 添加文书档案
     *
     * @param officialContent 文书档案内容
     */
    void add(OfficialContent officialContent);

    /**
     * 删除文书档案
     *
     * @param id 文书档案图片id
     */
    void delete(Integer id);

    /**
     * 获取指定文书档案id下所有图片内容地址
     * @param officialDocumentId 文书档案id
     * @return List<OfficialContent>
     */
    List<OfficialContent>getAllData(Integer officialDocumentId);



}
