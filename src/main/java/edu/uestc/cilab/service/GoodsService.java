package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.Goods;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.util.PageUtil;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 商品接口
 **/
public interface GoodsService {
    /**
     * 添加商品
     *
     * @param  goods 商品
     * @author zhangfeng
     * @throws  ExistException
     */
    void add(Goods goods) throws ExistException;

    /**
     * 删除商品
     *
     * @param id 商品id
     * @author zhangfeng
     */
    void delete(Integer id);

    /**
     * 更新商品
     * @param goods 商品
     * @author zhangfeng
     * @throws  ExistException
     */
    void update(Goods goods) throws ExistException ;

    /**
     * 查询商品列表
     * @param goodsType 商品类型
     * @param title 标题
     * @param pageNumber 页码
     * @param pageSize 页面大小
     * @param sortName 排序字段
     * @param sortOrder 排序方式
     * @return PageUtil<Goods>
     */
    PageUtil<Goods> selectByParameter(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, String goodsType, String title);

    /**
     * 查询商品详情
     * @param id 商品id
     * @author zhangfeng
     * @return Goods
     */
    Goods selectById(Integer id);
}
