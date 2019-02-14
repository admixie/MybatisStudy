package dao;

import model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 和message 数据表相关的操作都在这里写就好
 * 这个类使用的是JDBC原始的写法。
 */
public class MessageDB {
    public ArrayList<Message> SelectAll() throws SQLException {
        Connection connection = DButils.getConn();
        String sql = "select * from message";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        ArrayList<Message> messagelist = new ArrayList<>();
        ps = connection.prepareStatement(sql);
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Message message = new Message();
            message.setCOMMAND(resultSet.getString("COMMAND"));
            message.setCONTENT(resultSet.getString("CONTENT"));
            message.setID(resultSet.getString("ID"));
            message.setDESCRIPTION(resultSet.getString("DESCRIPTION"));
            messagelist.add(message);
        }
        return messagelist;
    }
    public ArrayList<Message> SelectByParm(String command,String description) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from message where 1 ");
        ArrayList<String> parm = new ArrayList<>();
        if (!command.trim().equals("")) {
            sql.append(" and COMMAND like '%' ? '%' ");
            parm.add(command);
        }
        if (!description.trim().equals("")) {
            sql.append(" and DESCRIPTION like '%' ? '%' ");
            parm.add(description);
        }
        Connection connection = DButils.getConn();
        ResultSet resultSet = null;
        ArrayList<Message> messagelist = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(sql.toString());
        for (Integer i = 0; i < parm.size(); i++) {
            ps.setString(i + 1, parm.get(i));
        }
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Message message = new Message();
            message.setCOMMAND(resultSet.getString("COMMAND"));
            message.setCONTENT(resultSet.getString("CONTENT"));
            message.setID(resultSet.getString("ID"));
            message.setDESCRIPTION(resultSet.getString("DESCRIPTION"));
            messagelist.add(message);
        }
        return messagelist;
    }
}
