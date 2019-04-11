package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.PageConstant;
import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SortConstant;
import edu.uestc.cilab.entity.OfficialBox;
import edu.uestc.cilab.entity.OfficialContent;
import edu.uestc.cilab.entity.OfficialDocument;
import edu.uestc.cilab.entity.vo.OfficialDocumentExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.repository.OfficialBoxMapper;
import edu.uestc.cilab.repository.OfficialContentMapper;
import edu.uestc.cilab.repository.OfficialDocumentMapper;
import edu.uestc.cilab.service.OfficialDocumentService;
import edu.uestc.cilab.util.ExportExcel;
import edu.uestc.cilab.util.PageUtil;
import edu.uestc.cilab.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 文书档案controller
 **/
@Controller
public class OfficialDocumentController {
    Logger logger = LoggerFactory.getLogger(OfficialDocumentController.class);
    @Autowired
    OfficialDocumentService officialDocumentService;
    @Autowired
    OfficialDocumentMapper officialDocumentMapper;
    @Autowired
    OfficialContentMapper officialContentMapper;
    @Autowired
    OfficialBoxMapper officialBoxMapper;
    @Autowired
    HttpSession session;

    @RequestMapping(value = {"/admin/officialDocument/add", "/user/officialDocument/add"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加文书档案")
    public ResultUtil add(@Valid @ModelAttribute OfficialDocument officialDocument, BindingResult result){
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            officialDocumentService.add(officialDocument);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }

        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加成功!", null);
    }
    @RequestMapping(value = {"/admin/officialDocument/delete/{id}", "/user/officialDocument/delete/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("删除文书档案（提示删除该文书档案下所有文件）")
    public ResultUtil delete(@ApiParam(value = "文书档案ID", required = true ) @PathVariable Integer id){
        officialDocumentService.delete(id);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/officialDocument/update", "/user/officialDocument/update"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改文书档案")
    public ResultUtil update(@Valid @ModelAttribute OfficialDocument officialDocument, BindingResult result)
    {
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        officialDocumentService.update(officialDocument);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"更新成功",null);
    }

    @RequestMapping(value = {"/admin/officialDocument/select", "/user/officialDocument/select"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("文书档案分类查询")
    public  ResultUtil<PageUtil<OfficialDocument>> officialDocumentSelect(@ApiParam("请求页码") @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                          @ApiParam("页面大小") @RequestParam(required = false, defaultValue = PageConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                                          @ApiParam("排序的字段") @RequestParam(required = false, defaultValue = "gmt_modified") String sortName,
                                                                          @ApiParam("排序字段的排序方式,只支持ASC、DESC") @RequestParam(required = false, defaultValue = SortConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                                          @ApiParam("盒子id") @RequestParam(required = false) Integer officialBoxId,
                                                                          @ApiParam("档号") @RequestParam(required = false) String boxNumber,
                                                                          @ApiParam("文号") @RequestParam(required = false) String number,
                                                                          @ApiParam("责任者") @RequestParam(required = false) String responsiblePerson,
                                                                          @ApiParam("题名") @RequestParam(required = false) String title,
                                                                          @ApiParam("保管期限") @RequestParam(required = false) String keepTime,
                                                                          @ApiParam("录入员") @RequestParam(required = false) String createUserName)
    {
        if (pageSize > PageConstant.PAGE_MAX_SIZE) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "当前页请求的数据太大!", null);
        }

        if (!SortConstant.isValidateSortOrder(sortOrder)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "排序参数错误!", null);
        }
        PageUtil<OfficialDocument> officialDocuments = officialDocumentService.select(pageNumber,pageSize,sortName,sortOrder,officialBoxId,boxNumber,number,responsiblePerson,title,keepTime,createUserName);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",officialDocuments);
    }

    @RequestMapping(value = {"/admin/officialDocument/export", "/user/officialDocument/export"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("文书档案导出Excel")
    public void export(HttpServletRequest request, HttpServletResponse response,@ApiParam("盒子id") @RequestParam(required = true) Integer officialBoxId) {
        //需要导入alibaba的fastjson包
       List<OfficialDocumentExcelVo> officialDocumentExcelVoList= officialDocumentService.getAllData(officialBoxId);
        ExportExcel<OfficialDocumentExcelVo> exportExcel = new ExportExcel<OfficialDocumentExcelVo>();
        String[] headers = {"序号", "档号", "文号", "责任者", "题名", "日期", "页数", "备注"};
        String boxNumber = officialBoxMapper.selectByPrimaryKey(officialBoxId).getBoxNumber();
        String fileName = "文书档案第"+ boxNumber + "盒";
        exportExcel.exportExcel(headers, officialDocumentExcelVoList, fileName,response);
    }

    @RequestMapping(value = {"/admin/officialDocument/backups", "/user/officialDocument/backups"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("一键备份所有文书档案")
    public ResultUtil backups(@ApiParam("备份文件保存路径") @RequestParam(required = true) String destPath) {
//        Boolean flag = false;
        File file = new File(destPath);
        if (!file.exists()) { // 源文件或源文件夹不存在
            return new ResultUtil(ResponseConstant.ResponseCode.FAILURE,"你指定的路径不存在",null);
        }
        //在指定路径下创建文书档案
        String officialFileNamePath = destPath + "/" + "文书档案";
        File prjectFile = new File(officialFileNamePath);
        if(!prjectFile.exists()){//如果文件夹不存在
            prjectFile.mkdirs();//创建文件夹
        }
        //获取所有文书档案盒号
        List<OfficialBox> officialBoxList = officialBoxMapper.getNumberList();


        //循环创建指定盒号的文件夹
        for (int i = 0; i<officialBoxList.size(); i++){
            String boxNumber = officialBoxList.get(i).getBoxNumber();
            int officialBoxId = officialBoxList.get(i).getId();
            //盒子路径
            String officialBoxFile = officialFileNamePath + "/" + "档号" + boxNumber;
            File boxNumberFile1 = new File(officialBoxFile);
            if(!boxNumberFile1.exists()){//如果文件夹不存在
                boxNumberFile1.mkdirs();//创建文件夹
            }
            //获取指定盒号的所有文件
            List<OfficialDocument> officialDocumentList = officialDocumentMapper.selectAllData(officialBoxId);
            for (OfficialDocument officialDocument:officialDocumentList) {
                //创建名称为id + number的文件夹
                String id = officialDocument.getId().toString();
                String number = officialDocument.getNumber();
                String officialDocumentFile = officialBoxFile + "/" + "序号"+id + "文号" + number;
                File documentFile = new File(officialDocumentFile);
                if(!documentFile.exists()){//如果文件夹不存在
                    documentFile.mkdirs();//创建文件夹
                }
                //获取指定document_id的所有图片地址
                List<OfficialContent> officialContentList = officialContentMapper.selectAlldata(officialDocument.getId());
                //循环复制指定图片到相应document文件夹
                for (OfficialContent officialContent:officialContentList) {
                    //图片源文件地址
                    String pictureSouAddress = officialContent.getContentAddress();
//                    String pictureName = pictureSouAddress.replaceAll("/home/projectDocumentImage/","");
                    String pictureName1 = pictureSouAddress.substring(41);

                    //复制
                    File sourFile = new File(pictureSouAddress);
                    File destFile = new File(documentFile + "/" + pictureName1);
//
                    try {
                        FileUtils.copyFile(sourFile,destFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"备份成功",null);
    }

}
