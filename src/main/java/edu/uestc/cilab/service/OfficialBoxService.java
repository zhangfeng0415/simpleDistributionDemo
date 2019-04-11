package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.OfficialBox;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 文书盒子接口
 **/
public interface OfficialBoxService {
    /**
     * 添加文书档案盒子
     *
     * @param  officialBox 文书档案盒子
     * @throws  ExistException
     */
    void add(OfficialBox officialBox) throws ExistException;

    /**
     * 删除文书档案盒子
     *
     * @param id 文书档案盒子id
     */
    void delete(Integer id);

    /**
     * 更新文书档案盒号
     *
     * @param officialBox 文书档案盒子
     * @throws  ExistException
     */
    void update(OfficialBox officialBox) throws ExistException ;

    /**
     * 查询文书档案盒子
     *
     * @param boxNumber 盒号
     * @param pageNumber 页码
     * @param pageSize 页面大小
     * @param sortName 排序字段
     * @param sortOrder 排序方式
     * @return PageUtil<OfficialBox>
     */
    PageUtil<OfficialBox> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,String boxNumber);
}
