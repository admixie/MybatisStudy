package dao;

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

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
