package servlet;

import model.Message;
import service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 这是使用mybatis进行数据库交互的servlet
 * 这个方法提供了一个有参数和没有参数的统一方法。
 */
@WebServlet("/MybatisListServlet")
public class MybatisListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //下面两个参数没有办法知道有没有值或者是空格等无效字符
        String command=request.getParameter("keyword");
        String description=request.getParameter("description");
        System.out.println(command);
        System.out.println(description);
        MessageService service=new MessageService();
        List<Message> messagelist=service.Select(command,description);
        System.out.println(messagelist.size());
        System.out.println("----------------");
        for(Message item:messagelist){
            System.out.println(item);
        }
        request.setAttribute("messagelist",messagelist);
        request.setAttribute("description",description);
        request.setAttribute("keyword",command);
        request.getRequestDispatcher("WEB-INF/jsp/back/mybatislist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
