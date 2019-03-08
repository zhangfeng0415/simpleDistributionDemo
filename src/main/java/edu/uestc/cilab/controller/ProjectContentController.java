package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.entity.ProjectContent;
import edu.uestc.cilab.repository.ProjectContentMapper;
import edu.uestc.cilab.service.ProjectContentService;
import edu.uestc.cilab.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by zhangfeng on 2018/1/15.
 */
@Controller
public class ProjectContentController {
    Logger logger = LoggerFactory.getLogger(ProjectContentController.class);
    @Autowired
    ProjectContentService projectContentService;
    @Autowired
    ProjectContentMapper projectContentMapper;
    @Autowired
    HttpSession session;
    @Value("#{configProperties['jdbc.projectDocument.image.path']}")
    String projectDocumentImage;

    @RequestMapping(value = {"/admin/projectContent/uploadImage", "/user/projectContent/uploadImage"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传工程档案内容图片")
    public ResultUtil uploadImage(@ApiParam(value = "工程档案ID", required = true ) @RequestParam Integer id,
                                  @ApiParam(value = "上传文件流", required = true) @RequestParam("image") MultipartFile file)throws IOException {
        ProjectContent projectContent = new ProjectContent();
        projectContent.setProjectDocumentId(id);
        String localFile = System.currentTimeMillis() + file.getOriginalFilename();
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(projectDocumentImage,localFile));
        String imagePath = projectDocumentImage + localFile;
        projectContent.setContentAddress(imagePath);
        projectContentService.add(projectContent);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加图片成功!", null);
    }

    @RequestMapping(value = {"/admin/projectContent/delete/{id}", "/user/projectContent/delete/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("删除工程档案图片")
    public ResultUtil delete(@ApiParam(value = "工程档案图片ID", required = true ) @PathVariable Integer id){
        projectContentService.delete(id);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/projectContent/select", "/user/projectContent/select"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据指定工程档案id查询该文档下所有图片内容")
    public  ResultUtil officialContentSelect(@ApiParam("工程档案id") @RequestParam(required = true) Integer projectDocumentId)
    {
        List<ProjectContent> projectContents = projectContentService.getAllData(projectDocumentId);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",projectContents);
    }

    @RequestMapping(value = {"/admin/projectContent/getImage/{id}", "/user/projectContent/getImage/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取指定工程档案图片")
    public void getImage(@ApiParam(value = "工程档案图片ID", required = true ) @PathVariable Integer id,
                         HttpServletRequest request, HttpServletResponse response)throws IOException{
        ProjectContent projectContent = projectContentMapper.selectByPrimaryKey(id);
        String path = projectContent.getContentAddress();
        String fileName = path.substring(40);
        String[] strArray = path.split("\\.");
        int suffixIndex = strArray.length -1;
        if (!path.equals("")){
            FileInputStream inputStream = new FileInputStream(path);
            int i = inputStream.available();
            //byte数组用于存放图片字节数据
            byte[] buff = new byte[i];
            inputStream.read(buff);
            //记得关闭输入流
            inputStream.close();
            //设置发送到客户端的响应内容类型
            response.setContentType("image/"+ strArray[suffixIndex]);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
            OutputStream out = response.getOutputStream();
            out.write(buff);
            out.flush();
            out.close();
        }else {
            return;
        }
    }
}
