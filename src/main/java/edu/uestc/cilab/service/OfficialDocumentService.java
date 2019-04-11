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
     * @author zhangfeng 作者
     */
    void add(OfficialDocument officialDocument) throws ExistException;

    /**
     * 删除用户
     *
     * @param id 文书档案id
     * @author zhangfeng 作者
     */
    void delete(Integer id);

    /**
     * 更新文书档案
     * @param officialDocument 用户
     * @author zhangfeng 作者
     */
    void update(OfficialDocument officialDocument);

    /**
     * 查询文书档案
     *
     * @author zhangfeng 作者
     */
    PageUtil<OfficialDocument>select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,Integer officialBoxId,String boxNumber, String number, String responsiblePerson, String title, String keepTime, String createUserName);

    /**
     * 获取所有文书档案信息并转换成Excel格式数据
     * @author zhangfeng 作者
     * @return
     */
    List<OfficialDocumentExcelVo>getAllData(Integer officialBoxId);
}
