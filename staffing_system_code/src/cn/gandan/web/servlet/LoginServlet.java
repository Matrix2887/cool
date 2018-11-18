package cn.gandan.web.servlet;

import cn.gandan.dao.MangerDao;
import cn.gandan.dao.impl.MangerDaoImpl;
import cn.gandan.domain.Manger;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        String CHECKCODE_SERVER = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        String verifycode = request.getParameter("verifycode");
        session.removeAttribute("CHECKCODE_SERVER");


        if(CHECKCODE_SERVER != null && CHECKCODE_SERVER.equalsIgnoreCase(verifycode)){

//===========================================================================================================================
            Map<String, String[]> map = request.getParameterMap();

            Manger manger = new Manger();

            try {
                BeanUtils.populate(manger,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
//            System.out.println(manger);

            MangerDao mangerDao = new MangerDaoImpl();
            Manger login = mangerDao.login(manger);
            if(login == null){
                request.setAttribute("login_error","用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }else {
                session.setAttribute("manger",manger);
                request.getRequestDispatcher("/successServlet").forward(request,response);
            }
//===========================================================================================================================

        }else {
            request.setAttribute("login_error","验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

