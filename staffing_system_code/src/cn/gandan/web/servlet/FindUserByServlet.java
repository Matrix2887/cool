package cn.gandan.web.servlet;

import cn.gandan.domain.PageBean;
import cn.gandan.domain.User;
import cn.gandan.service.UserService;
import cn.gandan.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet("/findUserByServlet")
public class FindUserByServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String currentPage = request.getParameter("currentPage"); // 当前页码
        String rows = request.getParameter("rows"); // 每页显示条数

        UserService service = new UserServiceImpl();
        if(currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if(rows == null || "".equals(rows)){
            rows = "5";
        }

        Map<String, String[]> condition = request.getParameterMap();

        PageBean<User> userByPage = service.findUserByPage(Integer.parseInt(currentPage), Integer.parseInt(rows),condition);

        System.out.println(userByPage);

//        System.out.println(userByPage);

        request.setAttribute("pb",userByPage);
        request.setAttribute("condition",condition);

        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
