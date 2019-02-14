package service;

import dao.MybatisMessageDB;
import model.Message;

import java.util.List;

/**
 * 一个用于message的service层 服务工具类；
 */
public class MessageService {
    public List<Message> Select(String command, String description){
        MybatisMessageDB db=new MybatisMessageDB();
        return db.Select(command,description);
    }
}
