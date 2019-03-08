package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.PageConstant;
import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SortConstant;
import edu.uestc.cilab.entity.OfficialContent;
import edu.uestc.cilab.repository.OfficialContentMapper;
import edu.uestc.cilab.service.OfficialContentService;
import edu.uestc.cilab.util.PageUtil;
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
public class OfficialContentController {
    Logger logger = LoggerFactory.getLogger(OfficialContentController.class);
    @Autowired
    OfficialContentService officialContentService;
    @Autowired
    OfficialContentMapper officialContentMapper;
    @Autowired
    HttpSession session;
    @Value("#{configProperties['jdbc.officialDocument.image.path']}")
    String officialDocumentImage;

    @RequestMapping(value = {"/admin/officialContent/uploadImage", "/user/officialContent/uploadImage"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传文书档案内容图片")
    public ResultUtil uploadImage(@ApiParam(value = "文书档案ID", required = true ) @RequestParam Integer id,
                                  @ApiParam(value = "上传文件流", required = true) @RequestParam("image") MultipartFile file)throws IOException {
        OfficialContent officialContent = new OfficialContent();
        officialContent.setOfficialDocumentId(id);
        String localFile = System.currentTimeMillis() + file.getOriginalFilename();
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(officialDocumentImage,localFile));
        String imagePath = officialDocumentImage + localFile;
        officialContent.setContentAddress(imagePath);
        officialContentService.add(officialContent);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加图片成功!", null);
    }

    @RequestMapping(value = {"/admin/officialContent/delete/{id}", "/user/officialContent/delete/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("删除文书档案图片")
    public ResultUtil delete(@ApiParam(value = "文书档案图片ID", required = true ) @PathVariable Integer id){
        officialContentService.delete(id);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/officialContent/select", "/user/officialContent/select"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据指定文书档案id查询该文档下所有图片内容")
    public  ResultUtil officialContentSelect(@ApiParam(value = "文书档案id", required = true) @RequestParam Integer officialDocumentId)
    {
        List<OfficialContent> officialContents = officialContentService.getAllData(officialDocumentId);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",officialContents);
    }

    @RequestMapping(value = {"/admin/officialContent/getImage/{id}", "/user/officialContent/getImage/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取指定文书档案图片")
    public void getImage(@ApiParam(value = "文书档案图片ID", required = true ) @PathVariable Integer id,
                         HttpServletRequest request, HttpServletResponse response)throws IOException{
        OfficialContent officialContent = officialContentMapper.selectByPrimaryKey(id);
        String path = officialContent.getContentAddress();
        String fileName = path.substring(41);
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
