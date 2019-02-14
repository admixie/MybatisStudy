package dao;

import model.Message;
import java.sql.*;
import java.util.ArrayList;

/**
 * 用于数据库连接的工具类
 * 经典JDBC写法，首先加载驱动，然后获取数据库连接
 * 注意下面的参数是固定的写法，其中 user 是用于登录数据库的用户名，password 是数据库的密码
 * 通过静态初始化块来加载驱动，只需要加载一次驱动就好。
 * 原生JDBC操作数据库是一个非常麻烦的过程，之后介绍的mybatis可以有效的解决这个问题。
 */
public class DButils {
    public static String url="jdbc:mysql://localhost:3308/mybatisdemo?useSSL=false&serverTimezone=UTC";
    public static String user="root";
    public static String password="root";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接的函数
     * @return 返回一个连接对象
     */
    public static Connection getConn()
    {
        try {
            Connection connection= DriverManager.getConnection(url,user,password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用于关闭一些资源的方法：
     * @param con  数据库连接对象
     * @param st   sql 语句对象
     */
    public static void close(Connection con, Statement st) {
        if(con!=null)
        {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st!=null)
        {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于关闭一些资源的方法：
     * @param con  数据库连接对象
     * @param st  sql语句对象
     * @param res  执行sql查询后的结果集对象
     */
    public static void close(Connection con, Statement st, ResultSet res) {
        if(res!=null)
        {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con!=null)
        {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st!=null)
        {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于测试类的静态方法，不用理会：
     * @param args
     */
    public static void main(String[] args){
        Connection connection=DButils.getConn();
        String sql="select * from message";
        PreparedStatement ps= null;
        ResultSet resultSet=null;
        ArrayList<Message> arr=new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            resultSet=ps.executeQuery();
            while(resultSet.next()){
                Message message=new Message();
                message.setCOMMAND(resultSet.getString("COMMAND"));
                message.setCONTENT(resultSet.getString("CONTENT"));
                message.setID(resultSet.getString("ID"));
                message.setDESCRIPTION(resultSet.getString("DESCRIPTION"));
                arr.add(message);
            }
            for(Message m:arr){
                System.out.println(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
