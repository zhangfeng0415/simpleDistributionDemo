package edu.uestc.cilab.service.impl;

import com.github.pagehelper.PageHelper;
import edu.uestc.cilab.entity.OfficialBox;
import edu.uestc.cilab.entity.OfficialBoxExample;
import edu.uestc.cilab.entity.OfficialDocument;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.repository.OfficialBoxMapper;
import edu.uestc.cilab.repository.OfficialDocumentMapper;
import edu.uestc.cilab.service.OfficialBoxService;
import edu.uestc.cilab.service.OfficialDocumentService;
import edu.uestc.cilab.util.DBUtil;
import edu.uestc.cilab.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 文书盒子接口实现层
 **/
@Service
public class OfficialBoxServiceImpl implements OfficialBoxService {

    @Autowired
    OfficialBoxMapper officialBoxMapper;
    @Autowired
    OfficialDocumentMapper officialDocumentMapper;
    @Autowired
    OfficialDocumentService officialDocumentService;

    @Override
    public void add(OfficialBox officialBox) throws ExistException {
        OfficialBoxExample example = new OfficialBoxExample();
        example.or().andBoxNumberEqualTo(officialBox.getBoxNumber());
        if (0 != officialBoxMapper.countByExample(example)){
            throw new ExistException("档案盒号重复");
        }
        officialBoxMapper.insertSelective(officialBox);
    }

    @Override
    public void delete(Integer id) {
        List<OfficialDocument> officialDocuments = officialDocumentMapper.selectAllData(id);
        for (OfficialDocument officialDocument:officialDocuments) {
            officialDocumentService.delete(officialDocument.getId());
        }
        officialBoxMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OfficialBox officialBox) throws ExistException {
        OfficialBoxExample example = new OfficialBoxExample();
        example.or().andBoxNumberEqualTo(officialBox.getBoxNumber());
        //取出原有数据
        OfficialBox officialBox1 = officialBoxMapper.selectByPrimaryKey(officialBox.getId());

        //盒号相同则直接修改,不同则校验
        if (officialBox1.getBoxNumber().equals(officialBox.getBoxNumber())){
            officialBoxMapper.updateByPrimaryKeySelective(officialBox);
        }else {
            if (0 != officialBoxMapper.countByExample(example)){
                //有重复则报错
                throw new ExistException("档案盒号重复");
            }
            //无重复则修改盒号，修改页数
            officialDocumentMapper.updateBoxNumberByOfficialBoxId(officialBox.getId(),officialBox.getBoxNumber());
            officialBoxMapper.updateByPrimaryKeySelective(officialBox);

        }

    }

    @Override
    public PageUtil<OfficialBox> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, String boxNumber) {

        PageHelper.startPage(pageNumber, pageSize);
        OfficialBoxExample officialBoxExample = new OfficialBoxExample();
        OfficialBoxExample.Criteria criteria = officialBoxExample.createCriteria();
        if (boxNumber != null){criteria.andBoxNumberLike("%" + boxNumber + "%"); }
        String orderByClause = DBUtil.getColumNameByProperty(sortName) + " " + sortOrder;
        officialBoxExample.setOrderByClause(orderByClause);
        List<OfficialBox> officialBoxes = officialBoxMapper.selectByExample(officialBoxExample);

        return new PageUtil<>(pageNumber, pageSize, officialBoxMapper.countByExample(officialBoxExample), officialBoxes);
    }
}
