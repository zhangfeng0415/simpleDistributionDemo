package edu.uestc.cilab.service.impl;

import com.github.pagehelper.PageHelper;
import edu.uestc.cilab.entity.Goods;
import edu.uestc.cilab.entity.GoodsExample;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.repository.GoodsMapper;
import edu.uestc.cilab.service.GoodsService;
import edu.uestc.cilab.util.DBUtil;
import edu.uestc.cilab.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 商品接口实现层
 **/
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public void add(Goods goods) throws ExistException {
        GoodsExample example = new GoodsExample();
        example.or().andTitleEqualTo(goods.getTitle());
        if (0 != goodsMapper.countByExample(example)){
            throw new ExistException("标题重复");
        }
        goodsMapper.insertSelective(goods);
    }

    @Override
    public void delete(Integer id) {
        goodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Goods goods) throws ExistException {
        GoodsExample example = new GoodsExample();
        example.or().andTitleEqualTo(goods.getTitle());
        if (0 != goodsMapper.countByExample(example)){
            throw new ExistException("标题重复");
        }
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public PageUtil<Goods> selectByParameter(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, String goodsType, String title) {
        PageHelper.startPage(pageNumber, pageSize);
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (goodsType !=null){ criteria.andGoodsTypeLike("%" + goodsType + "%"); }
        if (title !=null){ criteria.andTitleLike("%" + title + "%"); }
        String orderByClause = DBUtil.getColumNameByProperty(sortName) + " " + sortOrder;
        goodsExample.setOrderByClause(orderByClause);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return new PageUtil<>(pageNumber, pageSize, goodsMapper.countByExample(goodsExample), goodsList);
    }

    @Override
    public Goods selectById(Integer id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        return goods;
    }
}
