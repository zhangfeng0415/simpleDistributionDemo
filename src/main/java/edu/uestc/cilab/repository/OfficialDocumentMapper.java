package edu.uestc.cilab.repository;

import edu.uestc.cilab.entity.OfficialDocument;
import edu.uestc.cilab.entity.OfficialDocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description:
 **/
public interface OfficialDocumentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    int countByExample(OfficialDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    int deleteByExample(OfficialDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    @Delete({
        "delete from official_document",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    @Insert({
        "insert into official_document (id, official_box_id, ",
        "box_number, number, ",
        "title, page_number, ",
        "responsible_person, keep_time, ",
        "document_time, create_user_name, ",
        "gmt_create, gmt_modified)",
        "values (#{id,jdbcType=INTEGER}, #{officialBoxId,jdbcType=INTEGER}, ",
        "#{boxNumber,jdbcType=VARCHAR}, #{number,jdbcType=VARCHAR}, ",
        "#{title,jdbcType=VARCHAR}, #{pageNumber,jdbcType=VARCHAR}, ",
        "#{responsiblePerson,jdbcType=VARCHAR}, #{keepTime,jdbcType=VARCHAR}, ",
        "#{documentTime,jdbcType=VARCHAR}, #{createUserName,jdbcType=VARCHAR}, ",
        "#{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    int insert(OfficialDocument record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    int insertSelective(OfficialDocument record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    List<OfficialDocument> selectByExample(OfficialDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, official_box_id, box_number, number, title, page_number, responsible_person, ",
        "keep_time, document_time, create_user_name, gmt_create, gmt_modified",
        "from official_document",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    OfficialDocument selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") OfficialDocument record, @Param("example") OfficialDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") OfficialDocument record, @Param("example") OfficialDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OfficialDocument record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_document
     *
     * @mbggenerated
     */
    @Update({
        "update official_document",
        "set official_box_id = #{officialBoxId,jdbcType=INTEGER},",
          "box_number = #{boxNumber,jdbcType=VARCHAR},",
          "number = #{number,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "page_number = #{pageNumber,jdbcType=VARCHAR},",
          "responsible_person = #{responsiblePerson,jdbcType=VARCHAR},",
          "keep_time = #{keepTime,jdbcType=VARCHAR},",
          "document_time = #{documentTime,jdbcType=VARCHAR},",
          "create_user_name = #{createUserName,jdbcType=VARCHAR},",
          "gmt_create = #{gmtCreate,jdbcType=TIMESTAMP},",
          "gmt_modified = #{gmtModified,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OfficialDocument record);

    @Select({
            "select id, official_box_id, box_number, number, title,page_number,responsible_person,keep_time,document_time,create_user_name,gmt_create, gmt_modified ",
            "from official_document where official_box_id = #{officialBoxId,jdbcType=INTEGER} "
    })
    @ResultMap("BaseResultMap")
    List<OfficialDocument> selectAllData(Integer officialBoxId);

    @Select({
            "select COUNT(1) FROM official_document  WHERE number = #{number, jdbcType=VARCHAR} AND official_box_id =#{officialBoxId, jdbcType=INTEGER}"
    })
    int getSameNumber (@Param("number")String number, @Param("officialBoxId")Integer officialBoxId);

    @Update({
            "update official_document SET box_number = #{boxNumber, jdbcType=VARCHAR} WHERE official_box_id = #{officialBoxId, jdbcType=INTEGER} "
    })
    int updateBoxNumberByOfficialBoxId(@Param("officialBoxId")Integer officialBoxId,@Param("boxNumber")String boxNumber);
}