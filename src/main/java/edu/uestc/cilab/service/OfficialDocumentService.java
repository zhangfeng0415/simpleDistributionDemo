package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.OfficialDocument;
import edu.uestc.cilab.entity.vo.OfficialDocumentExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;

import java.util.List;

/**
 * Created by zhangfeng on 2018/1/12.
 */
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
