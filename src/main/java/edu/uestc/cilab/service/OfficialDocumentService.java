package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.OfficialDocument;
import edu.uestc.cilab.entity.vo.OfficialDocumentExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;

import java.util.List;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 文书文件接口
 **/
public interface OfficialDocumentService {

    /**
     * 添加文书档案
     *
     * @param officialDocument 文书档案
     * @throws  ExistException
     */
    void add(OfficialDocument officialDocument) throws ExistException;

    /**
     * 删除用户
     *
     * @param id 文书档案id
     */
    void delete(Integer id);

    /**
     * 更新文书档案
     *
     * @param officialDocument 文书档案
     */
    void update(OfficialDocument officialDocument);

    /**
     * 查询文书档案
     *
     * @param pageNumber 页码
     * @param pageSize 页面大小
     * @param sortName 排序字段
     * @param sortOrder 排序方式
     * @param officialBoxId 文书盒号id
     * @param boxNumber 盒号
     * @param number 编号
     * @param responsiblePerson 责任人
     * @param title 标题
     * @param keepTime 保存时间
     * @param createUserName 创造人名称
     * @return PageUtil<OfficialDocument>
     */
    PageUtil<OfficialDocument>select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,Integer officialBoxId,String boxNumber, String number, String responsiblePerson, String title, String keepTime, String createUserName);

    /**
     * 获取所有文书档案信息并转换成Excel格式数据
     *
     * @param officialBoxId 文书盒号id
     * @return List<OfficialDocumentExcelVo>
     */
    List<OfficialDocumentExcelVo>getAllData(Integer officialBoxId);
}
