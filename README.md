## 文档管理系统

### 基本介绍

本项目是一个集成了spring MVC + mybatis 。
#3.8start
-分布式系统开发
- 开发环境：
	- `JDK`: `1.8.0_11`
	- `Maven`: `Apache Maven 3.3.9`
	- `Tomcat`: `Tomcat8`
	- `Database`: `MySQL 5.5`

- mybatis的底层mapper的生成采用`mybatis generator`插件，mybatis的分页采用mybatis插件`PageHelper`

- 数据校验采用`JSR303`标准，具体实现采用`Hibernate Validator`

- 接口文档自动生成采用`Springfox`,访问`ip:port/education-website/swagger/index.html`即可查看接口

### 目录结构介绍

- `sql`:数据库表结构和数据导入导出文件
- `src/main`:代码所在目录
    - `java`:java源代码目录
        - `edu.uestc.cilab.config`:系统配置项
        - `edu.uestc.cilab.constant`:系统编码用到的常量区域
        - `edu.uestc.cilab.controller`:路由控制层
        - `edu.uestc.cilab.convert`:数据格式转换器(暂无)
        - `edu.uestc.cilab.entity`:实体层(数据库表实体的映射)
        - `edu.uestc.cilab.filter`:过滤器(权限控制)（暂无）
        - `edu.uestc.cilab.interceptor`:拦截器(权限控制)（暂无）
        - `edu.uestc.cilab.repository`:数据访问层,mybatis生成的Mapper接口
        - `edu.uestc.cilab.service`:业务逻辑层接口
            - `edu.uestc.cilab.service.impl`:业务逻辑层实现
        - `edu.uestc.cilab.util`:常用工具类（暂无）
    - `resources`:配置文件所在目录
        - mapper:mybatis映射配置文件目录
    - `webapp`:前端页面所在目录(包括html、CSS、Javascript等)
  
### 配置文件介绍
- `src/java/main/resources/generatorConfig.xml`:mybatis-generator插件所用配置文件
- `src/java/main/resources/jdbc.properties`:数据库连接配置
- `src/java/main/resources/log4j.properties`:日志配置
- `src/java/main/resources/mybatis-config.xml`:mybatis配置文件
- `src/java/main/resources/spring-mvc.xml`:spring-mvc配置文件
- `src/java/main/webapp/WEB-INF/web.xml`:web项目的入口配置文件


### 使用说明

> 使用前请先配置本项目需要的所有环境（包括maven一键部署方案，详见`参考学习文档`）

1. 本地新建数据库`website`导入`sql`目录下的文件(采用UTF-8编码)
2. 修改数据库配置文件`src/main/resources/jdbc.properties`(JDBC驱动包位置和数据库连接的用户名密码)
3. 运行`mvn tomcat7:deploy`或者`mvn tomcat7:redeploy`部署
4. 访问`ip:port/education-website/`即可访问系统
5. 访问`ip:port/education-website/swagger/index.html`即可查看系统所有的接口
 
### 编码约定

1. 老师操作的URL路径:`/admin/**`
2. 学生操作的URL路径:`/user/**`
5. 其它编码规范参考《阿里巴巴Java开发手册与规范.pdf》


--- 
**参考学习文档**
 
- [spring MVC 中文文档](http://7xvpsh.com1.z0.glb.clouddn.com/)
- [Springfox集成到Spring MVC项目](http://shildon.leanote.com/post/Springfox%E9%9B%86%E6%88%90%E5%88%B0%E9%A1%B9%E7%9B%AE)
- [mybatis的分页PageHelper介绍与使用](mybatis的分页采用mybatis插件[PageHelper])
- [mybatis generator介绍](https://github.com/mybatis/generator)
- [服务器maven一键部署方案](http://note.youdao.com/noteshare?id=7b90cd5e4763911d261e39b14d3f7b5d)
  