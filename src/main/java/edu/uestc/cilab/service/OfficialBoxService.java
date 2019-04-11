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
     * @author zhangfeng 作者
     */
    void add(OfficialBox officialBox) throws ExistException;

    /**
     * 删除文书档案盒子
     *
     * @param id 文书档案盒子id
     * @author zhangfeng 作者
     */
    void delete(Integer id);

    /**
     * 更新文书档案盒号
     * @param officialBox 文书档案盒子
     * @author zhangfeng 作者
     */
    void update(OfficialBox officialBox) throws ExistException ;

    /**
     * 查询文书档案盒子
     *
     * @author zhangfeng 作者
     */
    PageUtil<OfficialBox> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,String boxNumber);

    /**
     * 查询
     */
}
