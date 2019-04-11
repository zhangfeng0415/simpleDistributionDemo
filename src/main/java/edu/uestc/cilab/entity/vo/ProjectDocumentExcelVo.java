package edu.uestc.cilab.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 商品controller
 **/
@Setter
@Getter
@ToString
public class ProjectDocumentExcelVo {
    @ApiModelProperty(value = "序号，对应工程档案ID")
    private Integer id;

    @ApiModelProperty(value = "档号，对应档案盒号")
    private String box_number;

    @ApiModelProperty(value = "文号，对应档案编号")
    private String number;

    @ApiModelProperty(value = "责任者，对应责任人")
    private String responsiblePerson;

    @ApiModelProperty(value = "文件题名，对应题名")
    private String title;

    @ApiModelProperty(value = "日期，对应文件日期")
    private String documentTime;

    @ApiModelProperty(value = "页次")
    private String pageNumber;

    @ApiModelProperty(value = "备注")
    private String remark;

    public ProjectDocumentExcelVo(){super();}

}
