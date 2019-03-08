package edu.uestc.cilab.service.impl;

import com.github.pagehelper.PageHelper;
import edu.uestc.cilab.entity.OfficialContent;
import edu.uestc.cilab.entity.OfficialDocument;
import edu.uestc.cilab.entity.OfficialDocumentExample;
import edu.uestc.cilab.entity.vo.OfficialDocumentExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.repository.OfficialContentMapper;
import edu.uestc.cilab.repository.OfficialDocumentMapper;
import edu.uestc.cilab.service.OfficialContentService;
import edu.uestc.cilab.service.OfficialDocumentService;
import edu.uestc.cilab.util.DBUtil;
import edu.uestc.cilab.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangfeng on 2018/1/12.
 */
@Service
public class OfficialDocumentServiceImpl implements OfficialDocumentService{

    @Autowired
    OfficialDocumentMapper officialDocumentMapper;
    @Autowired
    OfficialContentMapper officialContentMapper;
    @Autowired
    OfficialContentService officialContentService;

    @Override
    public void add(OfficialDocument officialDocument)  throws ExistException{
//        if (0 != officialDocumentMapper.getSameNumber(officialDocument.getNumber(),officialDocument.getOfficialBoxId())){
//            throw new ExistException("盒号与档案编号重复");
//        }
        officialDocumentMapper.insertSelective(officialDocument);
    }

    @Override
    public void delete(Integer id) {
        List<OfficialContent> officialContents = officialContentMapper.selectAlldata(id);
        for (OfficialContent officialContent:officialContents) {
            officialContentService.delete(officialContent.getId());
        }
        officialDocumentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OfficialDocument officialDocument) {
        officialDocumentMapper.updateByPrimaryKeySelective(officialDocument);
    }

    @Override
    public PageUtil<OfficialDocument> select(Integer pageNumber, Integer pageSize, String sortName, String sortOrder,Integer officialBoxId,String boxNumber, String number, String responsiblePerson, String title, String keepTime, String createUserName) {

        PageHelper.startPage(pageNumber, pageSize);
        OfficialDocumentExample officialDocumentExample = new OfficialDocumentExample();
        OfficialDocumentExample.Criteria criteria = officialDocumentExample.createCriteria();
        if (officialBoxId != null){criteria.andOfficialBoxIdEqualTo(officialBoxId); }
        if (boxNumber != null){criteria.andBoxNumberEqualTo(boxNumber); }
        if (number != null){criteria.andNumberLike("%"+ number +"%"); }
        if (responsiblePerson != null){criteria.andResponsiblePersonLike("%"+ responsiblePerson + "%");}
        if (title != null){criteria.andTitleLike("%"+ title + "%");}
        if (keepTime != null){criteria.andKeepTimeLike("%"+ keepTime +"%");}
        if (createUserName != null){criteria.andCreateUserNameLike("%"+ createUserName +"%");}
        String orderByClause = DBUtil.getColumNameByProperty(sortName) + " " + sortOrder;
        officialDocumentExample.setOrderByClause(orderByClause);
        List<OfficialDocument> officialDocuments = officialDocumentMapper.selectByExample(officialDocumentExample);

        return new PageUtil<>(pageNumber, pageSize, officialDocumentMapper.countByExample(officialDocumentExample),officialDocuments);
    }

    @Override
    public List<OfficialDocumentExcelVo> getAllData(Integer officialBoxId) {
        List<OfficialDocument> officialDocumentList = officialDocumentMapper.selectAllData(officialBoxId);
        List<OfficialDocumentExcelVo> officialDocumentExcelVoList = new ArrayList<>();
        for (OfficialDocument officialDocument:officialDocumentList) {
            OfficialDocumentExcelVo officialDocumentExcelVo = new OfficialDocumentExcelVo();
            officialDocumentExcelVo.setId(officialDocument.getId());
            officialDocumentExcelVo.setBox_number(officialDocument.getBoxNumber());
            officialDocumentExcelVo.setNumber(officialDocument.getNumber());
            officialDocumentExcelVo.setResponsiblePerson(officialDocument.getResponsiblePerson());
            officialDocumentExcelVo.setTitle(officialDocument.getTitle());
            officialDocumentExcelVo.setDocumentTime(officialDocument.getDocumentTime());
            officialDocumentExcelVo.setPageNumber(officialDocument.getPageNumber());
            officialDocumentExcelVo.setRemark(null);
            officialDocumentExcelVoList.add(officialDocumentExcelVo);
        }
        return officialDocumentExcelVoList;
    }
}
