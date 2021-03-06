package edu.uestc.cilab.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description:
 **/
@Setter
@Getter
@ToString
public class OfficialBox {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column official_box.id
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "文书档案盒子ID，添加时无需指定")
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column official_box.box_number
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "盒号", required = true)
    private String boxNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column official_box.total_number
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "文件总数", required = false)
    private Integer totalNumber;

    public OfficialBox(Integer id, String boxNumber, Integer totalNumber) {
        this.id = id;
        this.boxNumber = boxNumber;
        this.totalNumber = totalNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    public OfficialBox() {
        super();
    }
}