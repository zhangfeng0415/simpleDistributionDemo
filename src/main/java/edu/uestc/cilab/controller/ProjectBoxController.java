package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.PageConstant;
import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SortConstant;
import edu.uestc.cilab.entity.ProjectBox;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.service.ProjectBoxService;
import edu.uestc.cilab.util.PageUtil;
import edu.uestc.cilab.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangfeng on 2018/3/6.
 */
@Controller
public class ProjectBoxController {

    Logger logger = LoggerFactory.getLogger(ProjectBoxController.class);
    @Autowired
    ProjectBoxService projectBoxService;
    @Autowired
    HttpSession session;

    @RequestMapping(value = {"/admin/projectBox/add", "/user/projectBox/add"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加工程档案盒子")
    public ResultUtil add(@Valid @ModelAttribute ProjectBox projectBox, BindingResult result){
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            projectBoxService.add(projectBox);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }

        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加成功!", null);
    }

    @RequestMapping(value = {"/admin/projectBox/delete/{id}", "/user/projectBox/delete/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("删除工程档案盒子（提示删除该盒子下所有文件）")
    public ResultUtil delete(@ApiParam(value = "工程档案ID", required = true ) @PathVariable Integer id){
        projectBoxService.delete(id);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/projectBox/update", "/user/projectBox/update"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改工程档案盒子")
    public ResultUtil update(@Valid @ModelAttribute ProjectBox projectBox, BindingResult result)
    {
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            projectBoxService.update(projectBox);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"更新成功",null);
    }

    @RequestMapping(value = {"/admin/projectBox/select", "/user/projectBox/select"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("工程档案盒子分类查询")
    public  ResultUtil<PageUtil<ProjectBox>> projectBoxSelect(@ApiParam("请求页码") @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                     @ApiParam("页面大小") @RequestParam(required = false, defaultValue = PageConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                                     @ApiParam("排序的字段") @RequestParam(required = false, defaultValue = "id") String sortName,
                                                                     @ApiParam("排序字段的排序方式,只支持ASC、DESC") @RequestParam(required = false, defaultValue = SortConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                                     @ApiParam("项目名称") @RequestParam(required = false) String projectName,
                                                                     @ApiParam("盒号") @RequestParam(required = false) String boxNumber)
    {
        if (pageSize > PageConstant.PAGE_MAX_SIZE) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "当前页请求的数据太大!", null);
        }

        if (!SortConstant.isValidateSortOrder(sortOrder)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "排序参数错误!", null);
        }
        PageUtil<ProjectBox> projectBoxs = projectBoxService.select(pageNumber,pageSize,sortName,sortOrder,projectName,boxNumber);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",projectBoxs);
    }

    @RequestMapping(value = {"/admin/projectBox/selectProjectNameList", "/user/projectBox/selectProjectNameList"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("查询项目名称列表（id倒序排列）")
    public  List<ProjectBox> selectProjectNameList()
    {
        List<ProjectBox> projectBoxs = projectBoxService.selectProjectList();
        return projectBoxs;
    }

    @RequestMapping(value = {"/admin/projectBox/selectProjectNameListByName", "/user/projectBox/selectProjectNameListByName"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("查询指定条件项目名称列表（id倒序排列）")
    public  List<ProjectBox> selectProjectNameListByName(@ApiParam("项目名称") @RequestParam(required = true) String projectName)
    {
        List<ProjectBox> projectBoxs = projectBoxService.getProjectListByName(projectName);
        return projectBoxs;
    }
}
