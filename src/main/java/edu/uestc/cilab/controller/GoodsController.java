package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.PageConstant;
import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SortConstant;
import edu.uestc.cilab.entity.Goods;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.service.GoodsService;
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
 * @description: 商品controller
 **/
@Controller
public class GoodsController {
    Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    GoodsService goodsService;
    @Autowired
    HttpSession session;

    @RequestMapping(value = {"/admin/goods/add"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加商品")
    public ResultUtil add(@Valid @ModelAttribute Goods goods, BindingResult result){
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            goodsService.add(goods);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }

        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加成功!", null);
    }

    @RequestMapping(value = {"/admin/goods/delete/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("删除商品")
    public ResultUtil delete(@ApiParam(value = "商品id", required = true ) @PathVariable Integer id){
        goodsService.delete(id);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/officialBox/update"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改商品信息")
    public ResultUtil update(@Valid @ModelAttribute Goods goods, BindingResult result)
    {
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            goodsService.update(goods);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"更新成功",null);
    }

    @RequestMapping(value = {"/admin/goods/goodsSelectByParameter", "/user/goods/goodsSelectByParameter"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据参数查询商品")
    public  ResultUtil<PageUtil<Goods>> goodsSelectByParameter(@ApiParam("请求页码") @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                @ApiParam("页面大小") @RequestParam(required = false, defaultValue = PageConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                                @ApiParam("排序的字段") @RequestParam(required = false, defaultValue = "id") String sortName,
                                                                @ApiParam("排序字段的排序方式,只支持ASC、DESC") @RequestParam(required = false, defaultValue = SortConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                                @ApiParam("商品类别") @RequestParam(required = false) String goodsType,
                                                                @ApiParam("盒号") @RequestParam(required = false) String title)
    {
        if (pageSize > PageConstant.PAGE_MAX_SIZE) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "当前页请求的数据太大!", null);
        }

        if (!SortConstant.isValidateSortOrder(sortOrder)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "排序参数错误!", null);
        }
        PageUtil<Goods> goodsList = goodsService.selectByParameter(pageNumber,pageSize,sortName,sortOrder,goodsType,title);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",goodsList);
    }

    @RequestMapping(value = {"/admin/goods/goodsSelectById", "/user/goods/goodsSelectById"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据id查询商品")
    public  ResultUtil<Goods> goodsSelectById(@ApiParam(value = "商品id", required = true ) @PathVariable Integer id)
    {
        Goods goods = goodsService.selectById(id);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",goods);
    }


}
