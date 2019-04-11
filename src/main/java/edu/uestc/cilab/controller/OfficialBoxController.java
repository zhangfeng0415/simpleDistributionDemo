package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.PageConstant;
import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SortConstant;
import edu.uestc.cilab.entity.OfficialBox;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.service.OfficialBoxService;
import edu.uestc.cilab.util.PageUtil;
import edu.uestc.cilab.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 文书盒子controller
 **/
@Controller
public class OfficialBoxController {

    Logger logger = LoggerFactory.getLogger(OfficialBoxController.class);
    @Autowired
    OfficialBoxService officialBoxService;
    @Autowired
    HttpSession session;

    @RequestMapping(value = {"/admin/officialBox/add", "/user/officialBox/add"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加文书档案盒子")
    public ResultUtil add(@Valid @ModelAttribute OfficialBox officialBox, BindingResult result){
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            officialBoxService.add(officialBox);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }

        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加成功!", null);
    }

    @RequestMapping(value = {"/admin/officialBox/delete/{id}", "/user/officialBox/delete/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("删除文书档案盒子（提示删除该盒子下所有文件）")
    public ResultUtil delete(@ApiParam(value = "文书档案ID", required = true ) @PathVariable Integer id){
        officialBoxService.delete(id);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/officialBox/update", "/user/officialBox/update"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改文书档案盒子(提示修改盒号可能会导致盒内外盒号不一致)")
    public ResultUtil update(@Valid @ModelAttribute OfficialBox officialBox, BindingResult result)
    {
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            officialBoxService.update(officialBox);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"更新成功",null);
    }

    @RequestMapping(value = {"/admin/officialBox/select", "/user/officialBox/select"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("文书档案盒子分类查询")
    public  ResultUtil<PageUtil<OfficialBox>> officialBoxSelect(@ApiParam("请求页码") @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                          @ApiParam("页面大小") @RequestParam(required = false, defaultValue = PageConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                                          @ApiParam("排序的字段") @RequestParam(required = false, defaultValue = "id") String sortName,
                                                                          @ApiParam("排序字段的排序方式,只支持ASC、DESC") @RequestParam(required = false, defaultValue = SortConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                                          @ApiParam("盒号") @RequestParam(required = false) String boxNumber)
    {
        if (pageSize > PageConstant.PAGE_MAX_SIZE) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "当前页请求的数据太大!", null);
        }

        if (!SortConstant.isValidateSortOrder(sortOrder)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "排序参数错误!", null);
        }
        PageUtil<OfficialBox> officialBoxs = officialBoxService.select(pageNumber,pageSize,sortName,sortOrder,boxNumber);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",officialBoxs);
    }
}
