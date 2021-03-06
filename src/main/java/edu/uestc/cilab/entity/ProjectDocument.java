package edu.uestc.cilab.entity;

import edu.uestc.cilab.constant.DateConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description:
 **/
@Setter
@Getter
@ToString
public class ProjectDocument {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.id
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "工程档案ID，添加时无需指定")
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.project_box_id
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "工程档案盒子id，添加时默认添加，前台不显示",required = true)
    private Integer projectBoxId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.box_number
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "盒号",required = true)
    private String boxNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.number
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "档案编号(可以不填)")
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.title
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "题名",required = true)
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.page_number
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "页数",required = true)
    private String pageNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.responsible_person
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "责任人",required = true)
    private String responsiblePerson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.keep_time
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "保管期限",required = true,allowableValues = "永久,100年,50年,30年,10年,长期,短期")
    @NotNull(message = "保管期限不能为空")
    private String keepTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.document_time
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "文件日期",required = true)
    private String documentTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.create_user_name
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "录入员",required = true)
    private String createUserName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.gmt_create
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = DateConstant.DATE_PATTERN)
    @ApiModelProperty(hidden = true)
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_document.gmt_modified
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = DateConstant.DATE_PATTERN)
    @ApiModelProperty(hidden = true)
    private Date gmtModified;

    public ProjectDocument(Integer id, Integer projectBoxId, String boxNumber, String number, String title, String pageNumber, String responsiblePerson, String keepTime, String documentTime, String createUserName, Date gmtCreate, Date gmtModified) {
        this.id = id;
        this.projectBoxId = projectBoxId;
        this.boxNumber = boxNumber;
        this.number = number;
        this.title = title;
        this.pageNumber = pageNumber;
        this.responsiblePerson = responsiblePerson;
        this.keepTime = keepTime;
        this.documentTime = documentTime;
        this.createUserName = createUserName;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    public ProjectDocument() {
        super();
    }
}