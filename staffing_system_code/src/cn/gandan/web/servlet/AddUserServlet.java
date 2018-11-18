package cn.gandan.web.servlet;

import cn.gandan.dao.UserDao;
import cn.gandan.dao.impl.UserDaoImpl;
import cn.gandan.domain.User;
import cn.gandan.service.UserService;
import cn.gandan.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

@WebServlet("/addUserServlet")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        Map<String, String[]> map = request.getParameterMap();
        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            String[] strings = map.get(s);
            System.out.println(s + "-->" + strings.toString() + "-->" + strings[0]);
        }

        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService userService = new UserServiceImpl();
        userService.addUser(user);

        response.sendRedirect(request.getContextPath()+"/findUserByServlet");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
