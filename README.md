# MybatisStudy

---

## 简介

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

## 官方网址

### [mybatis 官方网址](http://www.mybatis.org/mybatis-3/zh/index.html)

## 适合学习对象

- 已经掌握 Java 语言基础

- 已掌握 Java Web开发基础知识

- 已掌握 jsp，xml，servlet，sql，JDBC编程

## 开始使用

项目有两个英文项目文件夹，分别对应着两个不同的项目，一个用原生JDBC项目编写，一个用Mybatis 框架编写，实现了一个非常简单的查询功能。通过对比，可以体会mybatis的方便和强大之处。

- 开发环境：jetbrains Intelij Idea

- 类别：maven webapp项目（可以直接导入）

- 步骤：

  - 按照数据库导出文件提示，导入数据库或者自己建立一个相同的数据库。

  - 使用Intelij Idea打开两个项目，Maven 会将依赖的所有jar 包准备好

  - 配置tomcat 运行环境（原生web开发基础，这里不赘述方法）

  - 将核心配置文件中的数据库连接信息改成你自己的

  - 运行程序 （两个程序效果相同）

    - 使用原生JDBC的项目在tomcat重新启动后再浏览器中输入：http://localhost:8080/ListServlet

    - 使用Mybatis的项目在tomcat启动后在浏览器中输入http://localhost:8080/MybatisListServlet

## Mybatis 配置文件解析

### 核心配置文件：Configuration.xml

下面是一个核心配置文件示例，更多信息可以查看官网。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--这是mybatis的核心配置文件-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3308/mybatisdemo?useSSL=false&amp;serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <!--mappers 对应着各个sql语句的映射。-->
    <mappers>
        <mapper resource="Message.xml"/>
    </mappers>
</configuration>
```

### SQL 语句映射配置文件：Message.xml

这是为了数据库表Message 专门提供的SQL映射配置文件，当有多个数据表时候，建立多个这样的文件即可，然后需要在核心配置文件\<mappers>\</mappers>中加入映射路径地址。 

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--命名空间是 Message-->
<mapper namespace="Message">
    <!-- type 规定了结果集中元素的类型，也就是数据表的类型-->
    <resultMap id="map1" type="model.Message">
        <id column="ID" javaType="String" jdbcType="VARCHAR" property="ID"></id>
        <result column="COMMAND" javaType="String" jdbcType="VARCHAR" property="COMMAND"></result>
        <result column="DESCRIPTION" javaType="String" jdbcType="VARCHAR" property="DESCRIPTION"></result>
        <result column="CONTENT" javaType="String" jdbcType="VARCHAR" property="CONTENT"></result>
    </resultMap>
    <!--指定Select的方式，首先这个select设置的id是selectall，可以被java程序调用，resultMap 规定了结果的映射，parameterType规定了传入参数的类型，这里是Message类型-->
    <select id="selectall" resultMap="map1" parameterType="model.Message">
      select * from message
      <where>
          <if test="COMMAND!=null and !COMMAND.trim().equals(&quot;&quot;)">
              and COMMAND like '%' #{COMMAND} '%'
          </if>
          <if test="DESCRIPTION!=null and !DESCRIPTION.trim().equals(&quot;&quot;)">
              and DESCRIPTION like '%' #{DESCRIPTION} '%'
          </if>
      </where>
    </select>
</mapper>



<!--where 标签非常强大，可以在查询条件不是空且不是空白字符例如空格的情况下在sql语句后面加上where 子句和表达式，达到动态Sql的目的，非常方便-->
```

## Java读取配置文件

```java
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 这是使用mybatis 的数据库连接工具类
 * 相比于JDBC，更加的灵活和方便
 */
public class MybatisDButils {
    private static SqlSessionFactory sqlSessionFactory;
    static{
        //核心配置文件的地址：
        String src="Configuration.xml";
        try {
            //获得一个sqlSessionFactory，以后使用这个sqlSessionFactory 来打开会话
            InputStream input= Resources.getResourceAsStream(src);
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //向其他类提供一个获取SqlSession的接口，SqlSession后面会讲

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
```

## SqlSession

- ### SqlSession 作用

  - 向sql语句传入参数

  - 执行SQL语句

  - 获取执行sql语句的结果

  - 事务的控制

- ### 如何得到SqlSession：

  - 通过配置文件获取数据库连接相关信息

  - 通过配置信息构建SqlSessionFactory

  - 通过SqlSessionFactory打开一个数据库会话（也就是SqlSession）

## 执行数据库查询

```java
import model.Message;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是用mybatis 操作message 数据库的工具类。
 */
public class MybatisMessageDB {
    public List<Message> Select(String command, String description){
        List<Message> messagelist=new ArrayList<>();
        SqlSession session=null;
        try{
            //获取一个SqlSession对象用于操作数据库
            session=MybatisDButils.getSqlSessionFactory().openSession();
            //message 是一个数据表对应的JavaBean ，这里将参数封装为一个对象，实际上就是为了将查询条件都传递进selectList函数中，便于按条件查询
            Message message=new Message();
            message.setCOMMAND(command);
            message.setDESCRIPTION(description);
            //selectList函数返回一个结果列表，第一个参数规定了使用配置文件中的那一条Sql映射设置来进行查询，这个例子使用的是Message命名空间下id 为 selectall 的映射。可以翻看之前的Message配置文件
            messagelist=session.selectList("Message.selectall",message);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return messagelist;
    }
}
```
