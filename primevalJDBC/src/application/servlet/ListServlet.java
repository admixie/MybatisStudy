package servlet;

import dao.MessageDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * 这是一个控制层，用于写业务逻辑，这里实现的是从数据库中显示出message 数据表的所有数据。
 */
@WebServlet("/ListServlet")
public class ListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageDB messageDB=new MessageDB();
        ArrayList messagelist= new ArrayList();
        try {
            messagelist = messageDB.SelectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //将从数据库中取出的数据列表发送到request 域，前端就可以访问到了。
        request.setAttribute("messagelist",messagelist);
        //跳转到前端界面
        request.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String command=request.getParameter("keyword");
        String description=request.getParameter("description");

        MessageDB messageDB=new MessageDB();
        ArrayList messagelist=new ArrayList();
        try {
            messagelist = messageDB.SelectByParm(command,description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //将从数据库中取出的数据列表发送到request 域，前端就可以访问到了。
        request.setAttribute("messagelist",messagelist);
        request.setAttribute("description",description);
        request.setAttribute("keyword",command);
        //跳转到前端界面
        request.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(request,response);
    }
}
