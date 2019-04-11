package edu.uestc.cilab.repository;

import edu.uestc.cilab.entity.ProjectBox;
import edu.uestc.cilab.entity.ProjectBoxExample;
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
public interface ProjectBoxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    int countByExample(ProjectBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    int deleteByExample(ProjectBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    @Delete({
        "delete from project_box",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    @Insert({
        "insert into project_box (id, project_name, ",
        "box_number, total_number)",
        "values (#{id,jdbcType=INTEGER}, #{projectName,jdbcType=VARCHAR}, ",
        "#{boxNumber,jdbcType=VARCHAR}, #{totalNumber,jdbcType=INTEGER})"
    })
    int insert(ProjectBox record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    int insertSelective(ProjectBox record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    List<ProjectBox> selectByExample(ProjectBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, project_name, box_number, total_number",
        "from project_box",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ProjectBox selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ProjectBox record, @Param("example") ProjectBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ProjectBox record, @Param("example") ProjectBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProjectBox record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_box
     *
     * @mbggenerated
     */
    @Update({
        "update project_box",
        "set project_name = #{projectName,jdbcType=VARCHAR},",
          "box_number = #{boxNumber,jdbcType=VARCHAR},",
          "total_number = #{totalNumber,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProjectBox record);

    @Select({
            "select COUNT(1) FROM project_box  WHERE project_name = #{projectName, jdbcType=VARCHAR} AND box_number =#{boxNumber, jdbcType=VARCHAR}"
    })
    int getSameNumber (@Param("projectName")String projectName, @Param("boxNumber")String boxNumber);

    @Select({
            "select * FROM project_box  WHERE project_name = #{projectName, jdbcType=VARCHAR} "
    })
    @ResultMap("BaseResultMap")
    List<ProjectBox> getList (String projectName);

    /**
     * 查询所有项目名称列表
     * @return
     */
    @Select({
            "SELECT  * FROM project_box GROUP BY project_name ORDER BY id DESC"
    })
    @ResultMap("BaseResultMap")
    List<ProjectBox> getProjectNameList ();

    /**
     * 获取指定项目名称条件下的列表
     * @param projectName
     * @return
     */
    List<ProjectBox> getProjectNameListByName (@Param("projectName") String projectName);

    @Select({
            "SELECT  * FROM project_box WHERE project_name = #{projectName, jdbcType=VARCHAR} ORDER BY id ASC "
    })
    @ResultMap("BaseResultMap")
    List<ProjectBox> getBoxNumberList (String projectName);
}