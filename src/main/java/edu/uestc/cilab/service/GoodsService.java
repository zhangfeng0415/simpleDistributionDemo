package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.Goods;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;

/**
 * @author zhangfeng 2498711309@qq.com
 * @date 2019/3/22 17:44
 * @description：商品接口
 */
public interface GoodsService {
    /**
     * 添加商品
     *
     * @param  goods 商品
     * @author zhangfeng
     */
    void add(Goods goods) throws ExistException;

    /**
     * 删除商品
     *
     * @param id 文spid
     * @author zhangfeng
     */
    void delete(Integer id);

    /**
     * 更新商品
     * @param goods 商品
     * @author zhangfeng
     */
    void update(Goods goods) throws ExistException ;

    /**
     * 查询商品列表
     * @param goodsType 商品类型
     * @param title 标题
     * @author zhangfeng
     */
    PageUtil<Goods> selectByParameter(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, String goodsType, String title);

    /**
     * 查询商品详情
     *@ param id 商品id
     * @author zhangfeng
     */
    Goods selectById(Integer id);
}
