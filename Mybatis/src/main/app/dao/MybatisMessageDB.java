package dao;

import model.Message;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是用mybatis 才操作message 数据库的工具类。
 */
public class MybatisMessageDB {
    public List<Message> Select(String command, String description){
        List<Message> messagelist=new ArrayList<>();
        SqlSession session=null;
        try{
            session=MybatisDButils.getSqlSessionFactory().openSession();
            Message message=new Message();
            message.setCOMMAND(command);
            message.setDESCRIPTION(description);
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
